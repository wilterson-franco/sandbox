package com.wilterson.testslice;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MyController {

    private final PostService postService;

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {

        return postService.getPostById(1L)
                .map(post -> ResponseEntity.ok().body(post))
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

}
