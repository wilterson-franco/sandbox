package com.example.demo.token.dto;

import jakarta.validation.constraints.NotBlank;

public record AccessTokenRequest(
        @NotBlank String clientId,
        @NotBlank String clientSecret
) {

}