package com.bruno.trivia.dtos;
import java.time.LocalDateTime;

public record CompanyPropertiesResponseDTO(

        Long id,
        String name,
        String address,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
