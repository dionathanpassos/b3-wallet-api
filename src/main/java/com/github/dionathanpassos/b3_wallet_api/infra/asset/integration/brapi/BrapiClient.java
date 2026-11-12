package com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi;

import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi.dto.BrapiAssetQuoteResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.brapi.dto.BrapiAssetResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.exception.ApiUnavailableException;
import com.github.dionathanpassos.b3_wallet_api.exception.BrapiIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrapiClient {

    @Value("${api.key.token.brapi}")
private String token;

    private final RestClient brapiRestClient;

    public Optional<BrapiAssetQuoteResponseDTO> getTicker(String ticker) {

        try {
            BrapiAssetQuoteResponseDTO response = brapiRestClient.get()
                    .uri("https://brapi.dev/api/v2/stocks/quote?symbols={ticker}", ticker)
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> {
                        throw new BrapiIntegrationException("Erro na API Brapi. Status: " + res.getStatusCode());
                    })
                    .body(BrapiAssetQuoteResponseDTO.class);

            if(response != null && response.results() != null && !response.results().isEmpty()) {
                return Optional.of(response);
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
