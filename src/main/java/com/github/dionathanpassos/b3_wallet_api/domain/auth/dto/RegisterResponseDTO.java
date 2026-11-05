package com.github.dionathanpassos.tamplate_start_spring.domain.auth.dto;

import java.time.LocalDateTime;

public record RegisterResponseDTO(
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt
) {
}
