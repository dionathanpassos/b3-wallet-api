package com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto;

import com.github.dionathanpassos.b3_wallet_api.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record TransactionResponseDTO(
        Long id,
        Long accountId,
        Long assetId,
        TransactionType type,
        Long quantity,
        BigDecimal unitPrice,
        BigDecimal totalAmount,
        LocalDate transactionDate,
        Instant createdAt
) {
}

