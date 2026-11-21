package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi.dto;

import java.math.BigDecimal;
import java.util.List;

public record BrapiAssetQuoteHistoryResponseDTO(
        List<BrapiTickerHistoryPrice> results
) {
    public record BrapiTickerHistoryPrice(
            String symbol,
            BrapiHistoricalData data

    ) {}

    public record BrapiHistoricalData(
            List<BrapiHistoricalDataPrice> historicalDataPrice
    ) {}

    public record BrapiHistoricalDataPrice(
            Long date,
            BigDecimal close
    ) {}
}
