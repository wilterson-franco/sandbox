package com.wilterson.map;

import com.wilterson.dto.CartDto;
import com.wilterson.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface CartMapper {

    CartDto toDto(Cart cart);
}
