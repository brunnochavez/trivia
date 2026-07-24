package com.bruno.trivia.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryAddressRequestDTO(

        @NotNull
        Long neighborhoodId,

        @NotBlank(message = "Nome da rua é obrigatório")
        String street,

        @NotBlank(message = "Número da residência é obrigatório")
        String number,

        String complement,
        String referencePoint
) {
}
