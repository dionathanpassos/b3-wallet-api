package com.github.dionathanpassos.tamplate_start_spring.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        int status,
        String message,
        String error,
        String path,
        String method,
        LocalDateTime timestamp,
        Map<String, String> fieldErrors
) {
}
