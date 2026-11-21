package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ExternalAssetQuoteHistoryDTO(
        String ticker,
        LocalDate date,
        BigDecimal price
) {

}
