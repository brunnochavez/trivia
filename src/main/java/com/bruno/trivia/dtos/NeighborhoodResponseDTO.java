package com.bruno.trivia.dtos;
import java.math.BigDecimal;

public record NeighborhoodResponseDTO(

        Long id,
        String name,
        BigDecimal deliveryFee
) {
}
