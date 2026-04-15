package com.example.demo.api.dto;

import jakarta.validation.constraints.NotBlank;

public record IssuerInboundRequest(
        @NotBlank String clientId,
        @NotBlank String clientSecret,
        String payload
) {
}