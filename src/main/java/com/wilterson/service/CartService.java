package com.wilterson.service;

import com.wilterson.dto.CartDto;
import com.wilterson.map.CartMapper;
import com.wilterson.repository.CartRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public Optional<CartDto> getCart(Long cartId) {
        return cartRepository.findById(cartId).map(cartMapper::toDto);
    }

}
