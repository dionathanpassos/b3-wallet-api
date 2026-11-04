package com.github.dionathanpassos.tamplate_start_spring.domain.auth.dto;

import org.springframework.http.ResponseCookie;

public record LoginResponseDTO(
        String accessToken,
        ResponseCookie refreshToken
) {
}
