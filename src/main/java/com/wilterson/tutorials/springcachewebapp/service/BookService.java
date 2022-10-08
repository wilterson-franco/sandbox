package com.wilterson.tutorials.springcachewebapp.service;

import com.wilterson.tutorials.springcachewebapp.model.Book;
import com.wilterson.tutorials.springcachewebapp.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public Optional<Book> getBookByIsbn(String isbn) {
        return Optional.ofNullable(bookRepository.getByIsbn(isbn));
    }
}
