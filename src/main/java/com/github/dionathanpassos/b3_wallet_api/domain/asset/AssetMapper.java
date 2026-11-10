package com.github.dionathanpassos.b3_wallet_api.domain.asset;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.dto.AssetResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    AssetResponseDTO fromEntity(Asset asset);
}
