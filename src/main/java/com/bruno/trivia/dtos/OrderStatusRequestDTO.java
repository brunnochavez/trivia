package com.bruno.trivia.dtos;

import com.bruno.trivia.entities.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusRequestDTO(

        @NotNull
        OrderStatus status
) {
}
