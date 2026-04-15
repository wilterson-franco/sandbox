package com.example.demo.token;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TokenCoordinator {

    private final Cache<String, TokenValue> tokenCache;
    private final TokenClient tokenClient;
    private final Executor tokenFetchExecutor;

    /**
     * One future per clientId while a token request is in progress.
     */
    private final ConcurrentHashMap<String, CompletableFuture<TokenValue>> inFlight = new ConcurrentHashMap<>();

    public TokenCoordinator(
            Cache<String, TokenValue> tokenCache,
            TokenClient tokenClient,
            @Qualifier("tokenFetchExecutor") Executor tokenFetchExecutor) {
        this.tokenCache = tokenCache;
        this.tokenClient = tokenClient;
        this.tokenFetchExecutor = tokenFetchExecutor;
    }

    public CompletableFuture<TokenValue> prefetch(String clientId, String clientSecret) {
        TokenValue cached = tokenCache.getIfPresent(clientId);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }

        return inFlight.computeIfAbsent(clientId, key ->
                CompletableFuture.supplyAsync(() -> fetchAndCache(key, clientSecret), tokenFetchExecutor)
                        .whenComplete((result, throwable) -> inFlight.remove(key))
        );
    }

    private TokenValue fetchAndCache(String clientId, String clientSecret) {
        try {
            TokenValue tokenValue = tokenClient.requestToken(clientId, clientSecret)
                    .toFuture()
                    .join();

            tokenCache.put(clientId, tokenValue);
            return tokenValue;
        } catch (Exception ex) {
            throw new CompletionException(
                    new TokenAcquisitionException("Failed to obtain token for clientId=" + clientId, ex));
        }
    }
}