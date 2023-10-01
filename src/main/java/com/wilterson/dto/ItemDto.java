package com.wilterson.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ItemDto(String itemName, BigDecimal amount) {

}
