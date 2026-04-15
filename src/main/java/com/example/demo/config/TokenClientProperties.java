package com.example.demo.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "token-client")
public record TokenClientProperties(
        String baseUrl,
        String tokenPath,
        Duration connectTimeout,
        Duration responseTimeout,
        Duration cacheTtl,
        long maxCacheSize
) {
}