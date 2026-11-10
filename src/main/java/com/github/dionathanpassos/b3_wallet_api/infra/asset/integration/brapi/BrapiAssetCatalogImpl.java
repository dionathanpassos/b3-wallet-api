package com.github.dionathanpassos.b3_wallet_api.infra.integration.brapi;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.AssetType;
import com.github.dionathanpassos.b3_wallet_api.infra.integration.AssetCatalogProvider;
import com.github.dionathanpassos.b3_wallet_api.infra.integration.brapi.dto.BrapiAssetResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.infra.integration.ExternalAssetDTO;
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
    public Optional<ExternalAssetDTO> findByTicker(String ticker) {

        Optional<BrapiAssetResponseDTO.BrapiTickerResultDTO> response = brapiClient.getTicker(ticker);

        return response.map( tickerResponse -> new ExternalAssetDTO(
                tickerResponse.symbol(),
                tickerResponse.name(),
                AssetType.valueOf(tickerResponse.subType().toUpperCase()),
                tickerResponse.exchange(),
                tickerResponse.currency(),
                tickerResponse.sector(),
                tickerResponse.subsector(),
                tickerResponse.isActive(),
                tickerResponse.logoUrl()
            )
        );
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
