package com.wilterson.tutorials.springcachewebapp.service;

import com.wilterson.tutorials.springcachewebapp.model.Book;
import com.wilterson.tutorials.springcachewebapp.repository.BookRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Cacheable("books")
    public Optional<Book> getBookByIsbn(String isbn) {
        return Optional.ofNullable(bookRepository.findByIsbn(isbn));
    }
}
