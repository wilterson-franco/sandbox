package com.example.demo.service;

import com.example.demo.api.dto.IssuerInboundRequest;
import com.example.demo.config.IssuerProperties;
import com.example.demo.token.TokenAcquisitionException;
import com.example.demo.token.TokenCacheKey;
import com.example.demo.token.TokenCoordinator;
import com.example.demo.token.TokenTimeoutException;
import com.example.demo.token.TokenValue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class IssuerFlowService {

    private final TokenCoordinator tokenCoordinator;
    private final IssuerProperties issuerProperties;

    public IssuerFlowService(TokenCoordinator tokenCoordinator, IssuerProperties issuerProperties) {
        this.tokenCoordinator = tokenCoordinator;
        this.issuerProperties = issuerProperties;
    }

    public String process(IssuerInboundRequest request) {

        TokenCacheKey tokenCacheKey = new TokenCacheKey(request.clientId(), request.clientSecret());

        long timeoutMillis = issuerProperties.tokenWaitTimeout().toMillis();

        CompletableFuture<TokenValue> tokenFuture = tokenCoordinator.prefetch(tokenCacheKey, request.clientSecret(), timeoutMillis);

        doOtherTasks(request);

        try {
            TokenValue tokenValue = tokenFuture.get();

            return UriComponentsBuilder
                    .fromUriString(issuerProperties.targetUri())
                    .queryParam("token", tokenValue.accessToken())
                    .build(true)
                    .toUriString();

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new TokenAcquisitionException("Interrupted while waiting for token", ex);
        } catch (ExecutionException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof TimeoutException) {
                throw new TokenTimeoutException("Timed out waiting for token after %d ms total elapsed time".formatted(timeoutMillis), cause);
            }
            if (cause instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw new TokenAcquisitionException("Failed while waiting for token", cause);
        }
    }

    private void doOtherTasks(IssuerInboundRequest request) {
        // Put here the rest of your flow.
        // Validation, DB reads, message transformation, etc.
    }
}