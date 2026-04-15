package com.example.demo;

import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public final class MyWebClientFactory {

    private MyWebClientFactory() {
    }

    public static WebClient create() {
        ExchangeFunction fakeExchangeFunction = request -> {
            String json = """
                    {
                      "access_token": "token-123456",
                      "token_type": "bearer",
                      "expires_in": 900
                    }
                    """;

            ClientResponse response = ClientResponse.create(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(json)
                    .build();

//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }

            return Mono.just(response);
        };

        return WebClient.builder()
                .exchangeFunction(fakeExchangeFunction)
                .build();
    }
}