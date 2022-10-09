package com.wilterson.tutorials.springcachewebapp.controller;

import com.wilterson.tutorials.springcachewebapp.model.Book;
import com.wilterson.tutorials.springcachewebapp.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @CacheEvict(cacheNames = "books", cacheResolver = "myCacheResolver", key = "#isbn")
    @GetMapping("/books/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {

        Book book = bookService.getBookByIsbn(isbn).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        return ResponseEntity
                .ok()
                .body(book);
    }
}
