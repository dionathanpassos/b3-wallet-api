package com.github.dionathanpassos.b3_wallet_api.domain.broker.dto;

import jakarta.validation.constraints.NotBlank;

public record BrokerRequestDTO(
        @NotBlank(message = "O nome da corretora é obrigatório")
        String name
) {
}
