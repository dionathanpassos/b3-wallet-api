package com.github.dionathanpassos.b3_wallet_api.domain.auth.dto;

import java.time.LocalDateTime;

public record RegisterResponseDTO(
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt
) {
}
