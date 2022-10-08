package com.wilterson.tutorials.springcachewebapp.repository;

import com.wilterson.tutorials.springcachewebapp.model.Book;

public interface BookRepository {

    Book getByIsbn(String isbn);
}