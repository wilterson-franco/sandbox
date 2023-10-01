package com.wilterson.map;

import com.wilterson.dto.ItemDto;
import com.wilterson.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto toDto(Item item);
    Item toEntity(ItemDto itemDto);

}
