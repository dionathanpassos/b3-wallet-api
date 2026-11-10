package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi.dto;



import java.util.List;

public record BrapiAssetResponseDTO(
        List<BrapiTickerResultDTO> results,
        BrapiPaginationResponseDTO pagination
) {
    public record BrapiTickerResultDTO(
            String symbol,
            String name,
            String subType,
            String exchange,
            String currency,
            String sector,
            String subsector,
            Boolean isActive,
            String logoUrl
    ) {}

    public record BrapiPaginationResponseDTO(
            int totalPages
    ) {}
}




