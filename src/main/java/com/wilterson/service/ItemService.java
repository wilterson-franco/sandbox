package com.wilterson.service;

import com.wilterson.dto.ItemDto;
import com.wilterson.entity.Cart;
import com.wilterson.entity.Item;
import com.wilterson.exception.CartNotFoundException;
import com.wilterson.map.CartMapper;
import com.wilterson.map.ItemMapper;
import com.wilterson.repository.ItemRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final ItemMapper itemMapper;
    private final CartMapper cartMapper;

    public List<ItemDto> addItem(Set<ItemDto> itemDtos, Long cartId) {

        Cart cart = cartMapper.toEntity(cartService.getCart(cartId).orElseThrow(() -> new CartNotFoundException(String.format("Cart %d ", cartId))));

        List<Item> items = itemDtos.stream()
                .map(itemMapper::toEntity)
                .peek(item -> item.setCart(cart))
                .toList();

        return itemRepository.saveAll(items).stream().map(itemMapper::toDto).toList();
    }
}
