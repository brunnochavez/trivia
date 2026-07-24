package com.bruno.trivia.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record NeighborhoodRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @PositiveOrZero(message = "Taxa de entrega não pode ser negativa")
        @NotNull
        BigDecimal deliveryFee
) {
}
