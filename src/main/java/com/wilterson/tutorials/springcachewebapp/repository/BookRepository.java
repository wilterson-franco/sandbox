package com.wilterson.tutorials.springcachewebapp.repository;

import com.wilterson.tutorials.springcachewebapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

    Book findByIsbn(String isbn);
}
