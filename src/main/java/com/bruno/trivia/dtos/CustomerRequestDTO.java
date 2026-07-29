package com.bruno.trivia.dtos;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Telefone é obrigatório")
        String phone
) {
    public CustomerRequestDTO{
        name = name == null ? null : name.trim();
        phone = phone == null ? null : phone.trim();
    }
}
