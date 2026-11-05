package com.github.dionathanpassos.b3_wallet_api.domain.auth.dto;

import org.springframework.http.ResponseCookie;

public record LoginResponseDTO(
        String accessToken,
        ResponseCookie refreshToken
) {
}
