package com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto;

import com.github.dionathanpassos.b3_wallet_api.domain.transaction.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequestDTO(

        @NotNull(message = "A carteira é obridatória")
        Long accountId,

        @NotNull(message = "O ativo é obridatório")
        Long assetId,

        @NotNull(message = "A corretora é obridatória")
        Long brokerId,

        @NotNull(message = "O tipo da transação é obridatório")
        TransactionType type,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Long quantity,

        @NotNull(message = "O preço unitário é obrigatório")
        @Positive(message = "A preço deve ser maior que zero")
        BigDecimal unitPrice,

        @NotNull(message = "A data da transação é obrigatória")
        LocalDate transactionDate
) {
}
