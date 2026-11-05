package com.github.dionathanpassos.b3_wallet_api.domain.broker.dto;

import com.github.dionathanpassos.b3_wallet_api.domain.user.User;

import java.time.Instant;

public record BrokerResponseDTO(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
