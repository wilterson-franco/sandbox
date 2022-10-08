package com.wilterson.tutorials.springcache.caching;

public interface BookRepository {

    Book getByIsbn(String isbn);
}