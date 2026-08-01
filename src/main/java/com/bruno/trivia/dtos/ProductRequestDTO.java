package com.bruno.trivia.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public record ProductRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        String barcode,

        @Positive(message = "O preço de custo não pode ser negativo")
        @NotNull
        BigDecimal costPrice,

        @Positive(message = "O preço de venda não pode ser negativo")
        @NotNull
        BigDecimal salePrice,

        @PositiveOrZero(message = "Estoque não pode ser negativo")
        @NotNull
        Integer stockQuantity,

        List<String> ingredients,

        boolean active
) {
    public ProductRequestDTO{
        name = name == null ? null : name.trim();
        barcode = barcode == null ? null : barcode.trim();
    }
}
