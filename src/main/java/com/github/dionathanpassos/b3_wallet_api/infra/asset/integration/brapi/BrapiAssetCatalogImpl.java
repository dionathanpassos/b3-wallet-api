package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.AssetType;
import com.github.dionathanpassos.b3_wallet_api.exception.BusinessException;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.AssetCatalogProvider;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetQuoteDTO;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi.dto.BrapiAssetQuoteResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi.dto.BrapiAssetResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrapiAssetCatalogImpl implements AssetCatalogProvider {

    private final BrapiClient brapiClient;

    @Override
    public List<ExternalAssetQuoteDTO> findByTicker(String ticker) {

        BrapiAssetQuoteResponseDTO response = brapiClient.getTicker(ticker)
                .orElseThrow(() -> new BusinessException("Não foi possível obter a cotação do ativo"));

        return response.results().stream()
                .map(ticketInfo -> new ExternalAssetQuoteDTO(
                        ticketInfo.symbol(),
                        ticketInfo.data().regularMarketPrice(),
                        ticketInfo.data().regularMarketChange(),
                        ticketInfo.data().regularMarketChangePercent(),
                        ticketInfo.data().regularMarketDayHigh(),
                        ticketInfo.data().regularMarketDayLow(),
                        ticketInfo.data().regularMarketPreviousClose(),
                        ticketInfo.data().regularMarketVolume(),
                        ticketInfo.data().regularMarketTime()
                        )).toList();

    }

    @Override
    public List<ExternalAssetDTO> findAllTickers() {

        List<ExternalAssetDTO> allTickers = new ArrayList<>();
        int currentPage = 1;
        int totalPages = 1;

        do {
            Optional<BrapiAssetResponseDTO> responseOpt = brapiClient.getAllTickers(currentPage);

            if (responseOpt.isEmpty()) {
                break;
            }

            BrapiAssetResponseDTO response = responseOpt.get();

            if(response.pagination().totalPages() > 0) {
                totalPages = response.pagination().totalPages();
            }

            List<ExternalAssetDTO> pageItems = response.results().stream()
                    .map(tickerInfo -> new ExternalAssetDTO(
                            tickerInfo.symbol(),
                            tickerInfo.name(),
                            converterTypeAsset(tickerInfo.subType()),
                            tickerInfo.exchange(),
                            tickerInfo.currency(),
                            tickerInfo.sector(),
                            tickerInfo.subsector(),
                            tickerInfo.isActive(),
                            tickerInfo.logoUrl()
                    )).toList();

            allTickers.addAll(pageItems);

            currentPage++;
        } while (currentPage <= totalPages);

        return allTickers;

    }

    private AssetType converterTypeAsset(String subType) {
        if (subType == null) {
            return AssetType.STOCK;
        }

        return switch (subType.toLowerCase()) {
            case "fii" -> AssetType.FIIs;
            case "stock" -> AssetType.STOCK;
            default -> AssetType.STOCK;
        };
    }
}
