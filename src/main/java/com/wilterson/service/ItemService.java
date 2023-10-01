package com.wilterson.service;

import com.wilterson.entity.Cart;
import com.wilterson.entity.Item;
import com.wilterson.repository.ItemRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> addItem(Set<Item> items, Cart cart) {

        items.forEach(item -> item.setCart(cart));
        cart.setTotal(calculateTotal(items));
        return itemRepository.saveAll(items);
    }

    public static BigDecimal calculateTotal(Set<Item> items) {
        return items.stream().map(Item::getAmount).reduce(new BigDecimal("0"), BigDecimal::add);
    }
}
