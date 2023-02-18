package com.wilterson.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
public class BookClient {

    private final BookClientHttpInterface client;

    public BookClient(WebClient webClient) {
        client = bookClient(clientProxy(webClient));
    }

    public BookClientHttpInterface getBookClient() {
        return client;
    }

    private HttpServiceProxyFactory clientProxy(WebClient webClient) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
    }

    private BookClientHttpInterface bookClient(HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(BookClientHttpInterface.class);
    }
}
