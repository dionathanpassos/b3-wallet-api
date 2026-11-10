package com.github.dionathanpassos.b3_wallet_api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient brapiRestClient() {
        return RestClient.builder()
                .build();
    }
}
