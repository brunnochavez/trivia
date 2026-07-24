package com.bruno.trivia.dtos;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDTO(

        Long id,
        String name,
        String barcode,
        BigDecimal costPrice,
        BigDecimal salePrice,
        Integer stockQuantity,
        List<String> ingredients,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
