package com.wilterson.demo.controller;

import com.wilterson.demo.model.Post;
import com.wilterson.demo.service.PostClientHttpInterface;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    private final PostClientHttpInterface postClientHttp;

    public MyController(PostClientHttpInterface postClientHttp) {
        this.postClientHttp = postClientHttp;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postClientHttp.getPosts());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postClientHttp.getPostById(id));
    }
}
