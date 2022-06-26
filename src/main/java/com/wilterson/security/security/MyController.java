package com.wilterson.security.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/pub/greetings")
    public ResponseEntity<String> pubGreetings() {
        return ResponseEntity.ok("Pub Hello!");
    }

    @GetMapping("/sec/greetings")
    public ResponseEntity<String> secGreetings() {
        return ResponseEntity.ok("Sec Hello!");
    }

}
