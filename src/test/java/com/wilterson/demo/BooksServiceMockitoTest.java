package com.wilterson.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class BooksServiceMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @InjectMocks
    private BookClient bookClient;

    @Test
    @DisplayName("given mocked WebClient that returns two books, when getBooks method is called, then return a list of two Books")
    void givenMockedWebClientReturnsTwoBooks_WhenGetBooksServiceMethodIsCalled_ThenListOfTwoBooksIsReturned() {

        // given

        // when
        given(webClient.method(GET)
                .uri(anyString(), anyMap())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Book>>() {
                }))
                .willReturn(Mono.just(List.of(
                        new Book(1, "Book_1", "Author_1", 1998),
                        new Book(2, "Book_2", "Author_2", 2000))));

        List<Book> books = bookClient.getBookClient().getBooks();

        // then
        assertThat(books).hasSize(2);

        // verify

    }

    @Test
    @DisplayName("given mocked WebClient that returns a book, when getBook method is called, then return Book")
    void givenMockedWebClientReturnsBook_WhenGetBookServiceMethodIsCalled_ThenBookIsReturned() {

        // given

        // when
        given(webClient.method(GET)
                .uri(anyString(), anyMap())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Book>() {
                }))
                .willReturn(Mono.just(new Book(1, "Book_1", "Author_1", 1998)));

        Book book = bookClient.getBookClient().getBook(1);

        // then
        assertThat(book.id()).isEqualTo(1L);
        assertThat(book.title()).isEqualTo("Book_1");
        assertThat(book.author()).isEqualTo("Author_1");
        assertThat(book.year()).isEqualTo(1998);
    }

    @Test
    @DisplayName("given mocked WebClient that returns a book, when saveBook method is called, then return Book")
    void givenMockedWebClientReturnsBook_WhenSaveBookServiceMethodIsCalled_ThenBookIsReturned() {

        // given
        Book bookToReturn = new Book(1, "Book_1", "Author_1", 1998);
        Book bookToSave = new Book(1, "Book_1", "Author_1", 1998);

        // when
        given(webClient.method(POST)
                .uri(anyString(), anyMap())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Book>() {
                }))
                .willReturn(Mono.just(bookToReturn));

        Book book = bookClient.getBookClient().saveBook(bookToSave);

        // then
        assertThat(book.id()).isEqualTo(1L);
        assertThat(book.title()).isEqualTo("Book_1");
        assertThat(book.author()).isEqualTo("Author_1");
        assertThat(book.year()).isEqualTo(1998);
    }

    @Test
    @DisplayName("given mocked WebClient that returns OK, when deleteBook method is called, then return Book")
    void givenMockedWebClientReturnsBook_WhenDeleteBookServiceMethodIsCalled_ThenBookIsReturned() {

        // given
        Book bookToReturn = new Book(1, "Book_1", "Author_1", 1998);

        // when
        given(webClient.method(DELETE)
                .uri(anyString(), anyMap())
                .retrieve()
                .toBodilessEntity()
                .block(any())
                .getStatusCode())
                .willReturn(HttpStatusCode.valueOf(200));

        ResponseEntity<Void> resp = bookClient.getBookClient().deleteBook(1);

        // then
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }
}
