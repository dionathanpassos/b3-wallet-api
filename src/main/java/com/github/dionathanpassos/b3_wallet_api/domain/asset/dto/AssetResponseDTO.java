package com.github.dionathanpassos.b3_wallet_api.domain.asset.dto;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.AssetType;

public record AssetResponseDTO(
        String ticker,
        String name,
        AssetType type,
        String exchange,
        String currency,
        String sector,
        String subSector,
        boolean active,
        String logoUrl
) {
}
