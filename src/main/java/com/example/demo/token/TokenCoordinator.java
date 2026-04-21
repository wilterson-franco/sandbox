package com.example.demo.token;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TokenCoordinator {

    private static final Logger log = LoggerFactory.getLogger(TokenCoordinator.class);

    private final Cache<TokenCacheKey, TokenValue> tokenCache;
    private final TokenClient tokenClient;
    private final Executor tokenFetchExecutor;

    /**
     * One future per clientId while a token request is in progress.
     */
    private final ConcurrentHashMap<TokenCacheKey, CompletableFuture<TokenValue>> inFlight = new ConcurrentHashMap<>();

    public TokenCoordinator(Cache<TokenCacheKey, TokenValue> tokenCache, TokenClient tokenClient,
            @Qualifier("tokenFetchExecutor") Executor tokenFetchExecutor) {
        this.tokenCache = tokenCache;
        this.tokenClient = tokenClient;
        this.tokenFetchExecutor = tokenFetchExecutor;
    }

    public CompletableFuture<TokenValue> prefetch(TokenCacheKey tokenCacheKey, String clientSecret, long timeoutMillis) {

        TokenValue cached = tokenCache.getIfPresent(tokenCacheKey);

        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }

        log.info("Cache miss. Request token for clientId {}", tokenCacheKey.clientId());

        return inFlight.computeIfAbsent(tokenCacheKey,
                key -> CompletableFuture.supplyAsync(() -> fetchAndCache(key, clientSecret), tokenFetchExecutor)
                        .orTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
                        .whenComplete((result, throwable) -> inFlight.remove(key)));
    }

    private TokenValue fetchAndCache(TokenCacheKey tokenCacheKey, String clientSecret) {

        try {
            TokenValue tokenValue = tokenClient.requestToken(tokenCacheKey, clientSecret).toFuture().join();

            if (tokenValue == null) {
                throw new CompletionException(new TokenAcquisitionException("TokenValue is null for clientId=" + tokenCacheKey));
            }

            tokenCache.put(tokenCacheKey, tokenValue);

            return tokenValue;

        } catch (Exception ex) {
            throw new CompletionException(new TokenAcquisitionException("Failed to obtain token for clientId=" + tokenCacheKey, ex));
        }
    }
}