package com.github.dionathanpassos.b3_wallet_api.domain.assetquotehistory;

import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.AccountAssetRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.AssetCatalogProvider;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetQuoteDTO;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetQuoteHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssetQuoteHistoryService {

    private final AssetQuoteHistoryRepository assetQuoteHistoryRepository;
    private final AccountAssetRepository accountAssetRepository;
    private final AssetCatalogProvider assetCatalogProvider;

    public void saveQuoteHistory(Asset asset, ExternalAssetQuoteDTO quote) {

        LocalDate quoteDate = quote.quoteTime()
                .atZone(ZoneId.of("America/Sao_Paulo"))
                .toLocalDate();

        Optional<AssetQuoteHistory> historyOpt = assetQuoteHistoryRepository.findByAssetAndQuoteDate(asset, quoteDate);

        if (historyOpt.isPresent()) {
            return;
        }

        AssetQuoteHistory history = AssetQuoteHistory.create(
                asset,
                quote.price(),
                quoteDate
        );

        assetQuoteHistoryRepository.save(history);

    }

    @Scheduled(cron = "0 0 8 * * SAT", zone = "America/Sao_Paulo")
    public void synchronizeHistory() {
        List<Asset> assets = accountAssetRepository.findDistinctAssetsWithActivePositions();

        assets.forEach(asset -> {
            Set<LocalDate> existingDates = assetQuoteHistoryRepository.findExistingDatesByAsset(asset);

            List<ExternalAssetQuoteHistoryDTO> externalHistory = assetCatalogProvider.findHistoryQuoteByTicker(asset.getTicker());

           List<AssetQuoteHistory> newQuotes =  externalHistory.stream()
                   .filter(quote -> !existingDates.contains(quote.date()))
                   .map(quote -> AssetQuoteHistory.create(
                           asset,
                           quote.price(),
                           quote.date()
                   )).toList();

           assetQuoteHistoryRepository.saveAll(newQuotes);
        });

    }
}
