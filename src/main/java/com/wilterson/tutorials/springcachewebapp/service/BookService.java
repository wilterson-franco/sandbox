package com.wilterson.tutorials.springcachewebapp.service;

import com.wilterson.tutorials.springcachewebapp.model.Book;
import com.wilterson.tutorials.springcachewebapp.repository.BookRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Cacheable(cacheNames = "books", cacheResolver = "myCacheResolver", key = "#isbn")
    public Optional<Book> getBookByIsbn(String isbn) {
        log.info("Retrieving {} from the DB", isbn);
        return Optional.ofNullable(bookRepository.findByIsbn(isbn));
    }
}
