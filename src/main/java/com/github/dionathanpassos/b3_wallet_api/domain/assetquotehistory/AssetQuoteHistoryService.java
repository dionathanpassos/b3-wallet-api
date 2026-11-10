package com.github.dionathanpassos.b3_wallet_api.domain.assetquotehistory;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetQuoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetQuoteHistoryService {

    private final AssetQuoteHistoryRepository assetQuoteHistoryRepository;

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
}
