package com.github.dionathanpassos.b3_wallet_api.domain.assetquote;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.AssetRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.assetquotehistory.AssetQuoteHistoryService;
import com.github.dionathanpassos.b3_wallet_api.exception.ResourceNotFoundException;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetQuoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetQuotePersistenceService {

    private final AssetQuoteRepository assetQuoteRepository;
    private final AssetRepository assetRepository;
    private final AssetQuoteHistoryService assetQuoteHistoryService;

    @Transactional
    public void processQuote(ExternalAssetQuoteDTO quote) {

        Asset asset = assetRepository.findByTicker(quote.ticket())
                .orElseThrow(() -> new ResourceNotFoundException("Ativo não encontrado"));

        Optional<AssetQuote> assetQuoteOpt = assetQuoteRepository.findByAsset(asset);

        if (assetQuoteOpt.isPresent()) {
            AssetQuote assetQuote = assetQuoteOpt.get();

            assetQuote.updateQuote(
                    quote.price(),
                    quote.priceChange(),
                    quote.changePercent(),
                    quote.dayHigh(),
                    quote.dayLow(),
                    quote.previousClose(),
                    quote.volume(),
                    quote.quoteTime()
            );
        } else {
            AssetQuote assetQuote = AssetQuote.create(
                    asset,
                    quote.price(),
                    quote.priceChange(),
                    quote.changePercent(),
                    quote.dayHigh(),
                    quote.dayLow(),
                    quote.previousClose(),
                    quote.volume(),
                    quote.quoteTime()
            );
            assetQuoteRepository.save(assetQuote);
        }
        assetQuoteHistoryService.saveQuoteHistory(asset, quote);
    };

}
