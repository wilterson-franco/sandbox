package com.wilterson.testslice;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    public Optional<Post> getPostById(Long id) {
        return Optional.of(new Post(1L, "Greetings", "Wilterson"));
    }
}
