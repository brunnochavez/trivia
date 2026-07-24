package com.bruno.trivia.dtos;
import java.math.BigDecimal;

public record OrderItemResponseDTO(

        Long id,
        String productName,
        Integer quantity,
        BigDecimal salePrice,
        String observation
) {
}
