package com.wilterson.controller;

import com.wilterson.dto.ItemDto;
import com.wilterson.entity.Cart;
import com.wilterson.exception.CartNotFoundException;
import com.wilterson.map.ItemMapper;
import com.wilterson.service.CartService;
import com.wilterson.service.ItemService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final CartService cartService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping("/carts/{id}/items")
    public List<ItemDto> addItems(@PathVariable("id") Long cartId, @RequestBody List<ItemDto> itemDtos) {

        Cart cart = cartService.getCart(cartId).orElseThrow(() -> new CartNotFoundException(String.format("Cart %d ", cartId)));

        return itemService.addItem(itemDtos.stream().map(itemMapper::toEntity).collect(Collectors.toSet()), cart)
                .stream()
                .map(itemMapper::toDto)
                .toList();
    }
}
