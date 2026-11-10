package com.github.dionathanpassos.b3_wallet_api.domain.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRequestDTO(

        @NotBlank(message = "O nome da carteira é obrigatório")
        @Size(min = 4, max = 50, message = "O nome da carteira deve ter entre 4 e 20 caracteres")
        String name,

        @NotBlank(message = "A descrição não foi enviada ou está em branco")
        @Size(max = 120, message = "A descrição da carteira deve no máximo 50 caracteres")
        String description

) {
}
