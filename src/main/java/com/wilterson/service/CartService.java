package com.wilterson.service;

import com.wilterson.entity.Cart;
import com.wilterson.repository.CartRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Optional<Cart> getCart(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
