package com.bruno.trivia.services;
import com.bruno.trivia.dtos.PaymentMethodRequestDTO;
import com.bruno.trivia.dtos.PaymentMethodResponseDTO;
import com.bruno.trivia.entities.PaymentMethod;
import com.bruno.trivia.repositories.PaymentMethodRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional(readOnly = true)
    public PaymentMethodResponseDTO findById(Long id){
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Método de pagamento não encontrado!")
        );

        return toResponseDto(paymentMethod);
    }

    @Transactional(readOnly = true)
    public Page<PaymentMethodResponseDTO> findAll(Pageable pageable){
        Page<PaymentMethodResponseDTO> pagePaymentMethods = paymentMethodRepository.findAll(pageable)
                .map(p -> toResponseDto(p));
        return pagePaymentMethods;
    }

    @Transactional
    public PaymentMethodResponseDTO insert(PaymentMethodRequestDTO dto){
        if(paymentMethodRepository.existsByNameIgnoreCase(dto.name())){
            throw new EntityExistsException("Já existe um método de pagamento com este nome!");
        }
        PaymentMethod paymentMethod = toPaymentMethod(dto);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return toResponseDto(paymentMethod);
    }

    @Transactional
    public PaymentMethodResponseDTO update(Long id, PaymentMethodRequestDTO dto){
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Método de pagamento não encontrado!")
        );

        if(paymentMethodRepository.existsByNameIgnoreCaseAndIdNot(dto.name(), paymentMethod.getId())){
            throw new EntityExistsException("Já existe um método de pagamento com este nome");
        }

        dtoToEntity(dto, paymentMethod);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return toResponseDto(paymentMethod);
    }

    @Transactional
    public void deleteById(Long id){
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Método de pagamento não encontrado!")
        );
        paymentMethodRepository.delete(paymentMethod);
    }

    private PaymentMethodResponseDTO toResponseDto(PaymentMethod paymentMethod){
        return new PaymentMethodResponseDTO(
                paymentMethod.getId(),
                paymentMethod.getName(),
                paymentMethod.getFee()
        );
    }

    private PaymentMethod toPaymentMethod(PaymentMethodRequestDTO dto){
        return new PaymentMethod(
                dto.name(),
                dto.fee()
        );
    }

    private void dtoToEntity(PaymentMethodRequestDTO dto, PaymentMethod paymentMethod){
        paymentMethod.setName(dto.name());
        paymentMethod.setFee(dto.fee());
    }
}
