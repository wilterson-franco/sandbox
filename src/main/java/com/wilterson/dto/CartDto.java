package com.wilterson.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;

@Builder
public record CartDto(Long id, Set<ItemDto> items, BigDecimal total, LocalDateTime localDateTime) {

}
