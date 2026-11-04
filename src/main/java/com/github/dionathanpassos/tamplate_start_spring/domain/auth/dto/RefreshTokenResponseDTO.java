package com.github.dionathanpassos.tamplate_start_spring.domain.auth.dto;

import org.springframework.http.ResponseCookie;

public record RefreshTokenResponseDTO(
        String accessToken,
        ResponseCookie refreshToken
) {
}
