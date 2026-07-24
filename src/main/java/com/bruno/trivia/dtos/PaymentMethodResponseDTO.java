package com.bruno.trivia.dtos;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentMethodResponseDTO(

        Long id,
        String name,
        BigDecimal fee,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
