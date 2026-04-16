package com.example.demo.token;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public record TokenPrefetch(CompletableFuture<TokenValue> future, Instant startedAt) {

}