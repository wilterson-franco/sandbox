package com.wilterson.controller;

import com.wilterson.dto.CartDto;
import com.wilterson.exception.CartNotFoundException;
import com.wilterson.map.CartMapper;
import com.wilterson.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/carts/{id}")
    public CartDto getCart(@PathVariable Long id) {
        return cartMapper.toDto(cartService.getCart(id).orElseThrow(() -> new CartNotFoundException(String.format("Cart %d ", id))));
    }

    @PostMapping("/carts")
    public CartDto createCart(@RequestBody CartDto cartDto) {
        return cartMapper.toDto(cartService.createCart(cartMapper.toEntity(cartDto)));
    }
}
