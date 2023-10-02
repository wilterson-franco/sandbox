package com.wilterson.map;

import com.wilterson.dto.CartDto;
import com.wilterson.entity.Cart;
import com.wilterson.entity.Item;
import java.math.BigDecimal;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface CartMapper {

    @Mapping(target = "total", expression = "java(calculateTotal(cart.getItems()))")
    CartDto toDto(Cart cart);

    Cart toEntity(CartDto cartDto);

    default BigDecimal calculateTotal(Set<Item> items) {
        return items.stream().map(Item::getAmount).reduce(new BigDecimal("0"), BigDecimal::add);
    }
}
