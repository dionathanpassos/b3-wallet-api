package com.github.dionathanpassos.b3_wallet_api.infra.integration.brapi;

import com.github.dionathanpassos.b3_wallet_api.infra.integration.brapi.dto.BrapiAssetResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.exception.ApiUnavailableException;
import com.github.dionathanpassos.b3_wallet_api.exception.BrapiIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrapiClient {

    private final RestClient brapiRestClient;

    public Optional<BrapiAssetResponseDTO.BrapiTickerResultDTO> getTicker(String ticker) {

        try {
            BrapiAssetResponseDTO response = brapiRestClient.get()
                    .uri("https://brapi.dev/api/v2/tickers?search={ticker}", ticker)
                    .retrieve()
                    .body(BrapiAssetResponseDTO.class);

            if(response != null && response.results() != null && !response.results().isEmpty()) {
                return Optional.of(response.results().getFirst());
            }

            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<BrapiAssetResponseDTO> getAllTickers(int currentPage) {

        try {
            BrapiAssetResponseDTO response = brapiRestClient.get()
                    .uri("https://brapi.dev/api/v2/tickers?page={currentPage}", currentPage)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> {
                        throw new BrapiIntegrationException("Erro na API Brapi. Status: " + res.getStatusCode());
                    })
                    .body(BrapiAssetResponseDTO.class);

            if (response != null && response.results() != null && !response.results().isEmpty()) {
                return Optional.of(response);
            }

            return Optional.empty();
        } catch (ResourceAccessException e) {
            throw new ApiUnavailableException("Servidor temporariamente fora do ar");
        }
    }


}
