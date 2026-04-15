package com.example.demo.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "issuer")
public record IssuerProperties(
        String targetUri,
        Duration tokenWaitTimeout
) {
}