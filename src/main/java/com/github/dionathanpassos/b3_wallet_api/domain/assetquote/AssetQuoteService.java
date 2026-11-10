package com.github.dionathanpassos.b3_wallet_api.domain.assetquote;

import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.AccountAsset;
import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.AccountAssetRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.AssetCatalogProvider;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetQuoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetQuoteService {

    private final AssetQuotePersistenceService quotePersistenceService;
    private final AccountAssetRepository accountAssetRepository;
    private final AssetCatalogProvider assetCatalogProvider;




    @Scheduled(cron = "0 0 19 * * MON-FRI", zone = "America/Sao_Paulo")
    public void synchronizeAssetQuotes() {
        List<Asset> assets = accountAssetRepository.findDistinctAssetsWithActivePositions();

        assets.forEach(asset -> {
            List<ExternalAssetQuoteDTO> externalQuotes = assetCatalogProvider.findByTicker(asset.getTicker());
            externalQuotes.forEach(quotePersistenceService::processQuote);
        });
    }



}
