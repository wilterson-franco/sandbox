package com.example.demo.token;

import com.example.demo.MyWebClientFactory;
import com.example.demo.config.TokenClientProperties;
import com.example.demo.token.dto.AccessTokenRequest;
import com.example.demo.token.dto.AccessTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TokenClient {

    private final WebClient tokenWebClient;
    private final TokenClientProperties properties;

    public TokenClient(TokenClientProperties properties) {
        this.tokenWebClient = MyWebClientFactory.create();
        this.properties = properties;
    }

    public Mono<TokenValue> requestToken(TokenCacheKey tokenCacheKey, String clientSecret) {

        AccessTokenRequest request = new AccessTokenRequest(tokenCacheKey.clientId(), clientSecret);

        return tokenWebClient.post()
                .uri(properties.tokenPath())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .map(response -> {
                    if (response.accessToken() == null || response.accessToken().isBlank()) {
                        throw new TokenAcquisitionException("Token endpoint returned an empty access token");
                    }
                    return new TokenValue(response.accessToken());
                });
    }
}