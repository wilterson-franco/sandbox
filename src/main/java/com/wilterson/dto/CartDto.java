package com.wilterson.dto;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;

@Builder
public record CartDto(Set<ItemDto> items, BigDecimal total) {

}
