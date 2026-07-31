package com.bruno.trivia.exceptions;
import java.time.Instant;
import java.util.List;

public record ValidationErrorDTO(

        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<FieldMessage> fieldMessages
) {
}
