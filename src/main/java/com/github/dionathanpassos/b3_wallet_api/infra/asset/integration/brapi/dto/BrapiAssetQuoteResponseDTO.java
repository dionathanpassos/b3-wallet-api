package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record BrapiAssetQuoteResponseDTO(
        List<BrapiTicketQuoteResultDTO> results

) {
    public record BrapiTicketQuoteResultDTO(
            String symbol,
            boolean changed,
            BrapiTickerQuoteDataDTO data
    ) {}
    public record BrapiTickerQuoteDataDTO(
            BigDecimal regularMarketPrice,
            BigDecimal regularMarketChange,
            BigDecimal regularMarketChangePercent,
            BigDecimal regularMarketDayHigh,
            BigDecimal regularMarketDayLow,
            BigDecimal regularMarketPreviousClose,
            Long regularMarketVolume,
            Instant regularMarketTime
    ) {}


}
