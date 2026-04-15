package com.example.demo.token;

import java.util.Objects;

public record TokenCacheKey(String clientId, String correlationId) {

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof TokenCacheKey)) {
            return false;
        }

        var tokenCacheKey = (TokenCacheKey) obj;

        String thatClientId = tokenCacheKey.clientId;

        return this.clientId == thatClientId || this.clientId.equals(thatClientId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.clientId);
    }

    @Override
    public String toString() {
        return "TokenCacheKey's clientId: %s".formatted(clientId);
    }
}
