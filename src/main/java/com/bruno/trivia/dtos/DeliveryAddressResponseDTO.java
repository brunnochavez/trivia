package com.bruno.trivia.dtos;

public record DeliveryAddressResponseDTO(

        String neighborhoodName,
        String street,
        String number,
        String complement,
        String referencePoint
) {
}
