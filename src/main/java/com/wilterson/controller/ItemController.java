package com.wilterson.controller;

import com.wilterson.dto.ItemDto;
import com.wilterson.service.ItemService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/carts/{id}/items")
    public List<ItemDto> addItems(@PathVariable("id") Long cartId, @RequestBody Set<ItemDto> itemDtos) {
        return itemService.addItem(itemDtos, cartId);
    }
}
