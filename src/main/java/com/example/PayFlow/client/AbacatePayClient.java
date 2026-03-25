package com.example.PayFlow.client;

import com.example.PayFlow.dto.AbacateResponse;
import com.example.PayFlow.dto.PixRequestDTO;
import com.example.PayFlow.dto.PixResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AbacatePayClient {

    private final RestClient restClient;

    public AbacatePayClient(@Value("${abacatepay.api.url}") String baseUrl,
                            @Value("${abacatepay.api.token}") String token) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public PixResponseDTO createPix(PixRequestDTO request) {
        return restClient.post()
                .uri("/pixQrCode/create")
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<AbacateResponse<PixResponseDTO>>() {})
                .data();
    }
}
