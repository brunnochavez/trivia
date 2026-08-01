package com.bruno.trivia.dtos;
import com.bruno.trivia.entities.OrderStatus;
import com.bruno.trivia.entities.OrderType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(

        Long id,
        CustomerResponseDTO customer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatus status,
        BigDecimal deliveryFee,
        BigDecimal totalAmount,
        String paymentMethodName,
        OrderType orderType,
        DeliveryAddressResponseDTO delivery,
        List<OrderItemResponseDTO> items

) {
}
