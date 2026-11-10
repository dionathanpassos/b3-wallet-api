package com.github.dionathanpassos.b3_wallet_api.infra.integration;

import java.util.List;
import java.util.Optional;

public interface AssetCatalogProvider {

    Optional<ExternalAssetDTO> findByTicker(String ticker);

    List<ExternalAssetDTO> findAllTickers();

}
