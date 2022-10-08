package com.wilterson.tutorials.springcachewebapp.controller;

import com.wilterson.tutorials.springcachewebapp.model.Book;
import com.wilterson.tutorials.springcachewebapp.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/books/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {

        Book book = bookService.getBookByIsbn(isbn).orElseThrow(() -> new RuntimeException("Book not found"));

        return ResponseEntity
                .ok()
                .body(book);
    }
}
