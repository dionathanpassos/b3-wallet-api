package com.github.dionathanpassos.b3_wallet_api.domain.account.dto;

import com.github.dionathanpassos.b3_wallet_api.domain.account.AccountStatus;

public record AccountResponseDTO(
        Long id,
        String name,
        String description,
        AccountStatus status

) {
}
