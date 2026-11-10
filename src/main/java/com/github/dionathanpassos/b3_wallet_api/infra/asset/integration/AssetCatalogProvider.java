package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration;

import java.util.List;
import java.util.Optional;

public interface AssetCatalogProvider {

    List<ExternalAssetQuoteDTO> findByTicker(String ticker);

    List<ExternalAssetDTO> findAllTickers();

}
