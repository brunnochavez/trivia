package com.bruno.trivia.dtos;
import java.math.BigDecimal;

public record PaymentMethodResponseDTO(

        Long id,
        String name,
        BigDecimal fee
) {
}
