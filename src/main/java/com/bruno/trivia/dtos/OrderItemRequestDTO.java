package com.bruno.trivia.dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequestDTO(

        @NotNull(message = "Insira o código do produto")
        Long productId,

        @Positive(message = "Quantidade do produto não pode ser zero ou negativa")
        Integer quantity,

        String observation
) {
}
