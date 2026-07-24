package com.bruno.trivia.dtos;
import jakarta.validation.constraints.NotBlank;


public record CompanyPropertiesRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String name,

        @NotBlank(message = "Telefone obrigatório")
        String phone,

        @NotBlank(message = "Endereço obrigatório")
        String address
) {
}
