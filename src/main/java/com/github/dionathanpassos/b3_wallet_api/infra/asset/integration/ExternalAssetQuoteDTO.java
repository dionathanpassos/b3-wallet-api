package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration;

import java.math.BigDecimal;
import java.time.Instant;

public record ExternalAssetQuoteDTO(
        String ticket,
        BigDecimal price,
        BigDecimal priceChange,
        BigDecimal changePercent,
        BigDecimal dayHigh,
        BigDecimal dayLow,
        BigDecimal previousClose,
        Long volume,
        Instant quoteTime
) {
}
