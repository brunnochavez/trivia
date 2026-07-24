package com.bruno.trivia.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record PaymentMethodRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotNull
        @PositiveOrZero(message = "Taxa não pode ser negativa")
        BigDecimal fee
) {
}
