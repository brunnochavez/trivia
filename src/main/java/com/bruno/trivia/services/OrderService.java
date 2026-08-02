package com.bruno.trivia.services;
import com.bruno.trivia.dtos.*;
import com.bruno.trivia.entities.*;
import com.bruno.trivia.repositories.NeighborhoodRepository;
import com.bruno.trivia.repositories.OrderRepository;
import com.bruno.trivia.repositories.PaymentMethodRepository;
import com.bruno.trivia.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final PaymentMethodRepository paymentMethodRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Pedido não encontrado!")
        );

        return toResponseDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> findAll(Pageable pageable){
        Page<OrderResponseDTO> responseDTOPage = orderRepository.findAll(pageable)
                .map(o -> toResponseDto(o));
        return responseDTOPage;
    }


    @Transactional
    public OrderResponseDTO insert(OrderRequestDTO dto){
        Customer customer = customerService.createOrUpdate(dto.customer());

        PaymentMethod paymentMethod = paymentMethodRepository.findById(dto.paymentMethodId()).orElseThrow(
                () -> new EntityNotFoundException("Método de pagamento não encontrado!")
        );

        Neighborhood neighborhood = null;
        DeliveryAddress deliveryAddress = null;
        if(dto.orderType() == OrderType.DELIVERY){
            if(dto.delivery() == null){
                throw new IllegalArgumentException("É preciso informar o endereço de entrega");
            }
            neighborhood = neighborhoodRepository.findById(dto.delivery().neighborhoodId()).orElseThrow(
                    () -> new EntityNotFoundException("Bairro não encontrado!")
            );

            deliveryAddress = new DeliveryAddress();
            deliveryAddress.setStreet(dto.delivery().street());
            deliveryAddress.setNumber(dto.delivery().number());
            deliveryAddress.setComplement(dto.delivery().complement());
            deliveryAddress.setReferencePoint(dto.delivery().referencePoint());
        }

        Order order = new Order(
                dto.orderType(),
                deliveryAddress,
                neighborhood,
                customer,
                paymentMethod
        );

        BigDecimal deliveryFee = (neighborhood != null) ? neighborhood.getDeliveryFee() : BigDecimal.ZERO;

        for(OrderItemRequestDTO itemDto : dto.items()){
            Product product = productRepository.findByIdAndActiveTrue(itemDto.productId()).orElseThrow(
                    () -> new EntityNotFoundException("Produto não encontrado!")
            );

            if(product.getStockQuantity() < itemDto.quantity()){
                throw new IllegalArgumentException(product.getName() + " com estoque insuficiente!");
            }

            product.setStockQuantity(product.getStockQuantity() - itemDto.quantity());

            OrderItem orderItem = new OrderItem(
                    itemDto.quantity(),
                    product.getSalePrice(),
                    itemDto.observation(),
                    product
            );

            order.addOrderItem(orderItem);
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for(OrderItem item : order.getOrderItems()){
            totalAmount = totalAmount.add(item.getSalePrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        totalAmount = totalAmount.add(deliveryFee);

        order.setDeliveryFee(deliveryFee);
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        return toResponseDto(order);
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long id, OrderStatusRequestDTO dto){
        if(dto.status() == OrderStatus.CANCELED){
            throw new IllegalArgumentException("O pedido já foi cancelado anteriormente!");
        }

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Pedido não encontrado!")
        );

        validateStatusTransition(order.getStatus(), dto.status());
        order.setStatus(dto.status());
        order = orderRepository.save(order);
        return toResponseDto(order);
    }

    @Transactional
    public OrderResponseDTO cancel(Long id){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Pedido não encontrado!")
        );

        order.setStatus(OrderStatus.CANCELED);
        order = orderRepository.save(order);
        return toResponseDto(order);
    }

    private OrderResponseDTO toResponseDto(Order order) {
        CustomerResponseDTO customerDto = new CustomerResponseDTO(
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getCustomer().getPhone()
        );

        DeliveryAddressResponseDTO deliveryDto = null;
        if (order.getDeliveryAddress() != null) {
            deliveryDto = new DeliveryAddressResponseDTO(
                    order.getNeighborhood().getName(),
                    order.getDeliveryAddress().getStreet(),
                    order.getDeliveryAddress().getNumber(),
                    order.getDeliveryAddress().getComplement(),
                    order.getDeliveryAddress().getReferencePoint()
            );
        }

        List<OrderItemResponseDTO> itemsDto = order.getOrderItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSalePrice(),
                        item.getObservation()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                customerDto,
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getStatus(),
                order.getDeliveryFee(),
                order.getTotalAmount(),
                order.getPaymentMethod().getName(),
                order.getOrderType(),
                deliveryDto,
                itemsDto
        );
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus requested) {
        boolean valid = switch (current) {
            case RECEIVED -> requested == OrderStatus.PREPARING;
            case PREPARING -> requested == OrderStatus.READY;
            case READY -> requested == OrderStatus.COMPLETED;
            case COMPLETED, CANCELED -> false;
        };

        if (!valid) {
            throw new IllegalArgumentException("Transição de status inválida: " + current + " -> " + requested);
        }

    }
}
