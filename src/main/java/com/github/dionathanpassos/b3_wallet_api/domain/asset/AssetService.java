package com.github.dionathanpassos.b3_wallet_api.domain.asset;

import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.AssetCatalogProvider;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetCatalogProvider assetCatalogProvider;
    private final AssetRepository assetRepository;

    @Transactional
    @Scheduled(cron = "0 0 12 * * MON,THU")
    public void synchronizeAssets() {
        List<ExternalAssetDTO> externalAssets = assetCatalogProvider.findAllTickers();

        externalAssets.stream()
                .filter(externalAsset -> !externalAsset.ticker().toLowerCase().endsWith("f"))
                .forEach(externalAsset -> {

                    Optional<Asset> assetOpt = assetRepository.findByTicker(externalAsset.ticker());

                    if (assetOpt.isPresent()) {
                        Asset asset = assetOpt.get();

                        asset.updateAsset(
                                externalAsset.name(),
                                externalAsset.type(),
                                externalAsset.exchange(),
                                externalAsset.currency(),
                                externalAsset.sector(),
                                externalAsset.subSector(),
                                externalAsset.active(),
                                externalAsset.logoUrl()
                        );
                    } else {
                        Asset newAsset = Asset.createAsset(
                                externalAsset.ticker(),
                                externalAsset.name(),
                                externalAsset.type(),
                                externalAsset.exchange(),
                                externalAsset.currency(),
                                externalAsset.sector(),
                                externalAsset.subSector(),
                                externalAsset.active(),
                                externalAsset.logoUrl()
                        );
                        assetRepository.save(newAsset);
                    }
                });
    }
}
