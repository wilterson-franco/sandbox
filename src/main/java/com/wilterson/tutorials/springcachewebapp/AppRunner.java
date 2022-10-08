package com.wilterson.tutorials.springcachewebapp;

import com.wilterson.tutorials.springcachewebapp.model.Book;
import com.wilterson.tutorials.springcachewebapp.repository.BookRepository;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String[] args) {

        List<Book> books = Stream.of(
                Book.builder().isbn("1111").title("book 1").build(),
                Book.builder().isbn("2222").title("book 2").build(),
                Book.builder().isbn("3333").title("book 3").build(),
                Book.builder().isbn("4444").title("book 4").build(),
                Book.builder().isbn("5555").title("book 5").build()
        ).toList();

        bookRepository.saveAll(books);
    }
}
