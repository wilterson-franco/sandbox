package com.example.demo.config;

import com.example.demo.token.TokenCacheKey;
import com.example.demo.token.TokenValue;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TokenInfrastructureConfig {

    @Bean
    public Cache<TokenCacheKey, TokenValue> issuerTokenCache(TokenClientProperties properties) {
        return Caffeine.newBuilder()
                .expireAfterWrite(properties.cacheTtl())
                .maximumSize(properties.maxCacheSize())
                .build();
    }

    @Bean
    @Qualifier("tokenFetchExecutor")
    public Executor tokenFetchExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("token-fetch-");
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(5000);
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }
}