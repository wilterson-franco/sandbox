package com.wilterson.demo;

import static io.swagger.models.HttpMethod.DELETE;
import static io.swagger.models.HttpMethod.GET;
import static io.swagger.models.HttpMethod.POST;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.APPLICATION_JSON;
import static org.slf4j.event.Level.WARN;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.configuration.Configuration;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class BooksServiceMockServerTest {

    private static final String SERVER_ADDRESS = "localhost";
    private static final String PATH = "/books";

    private static int serverPort;
    private static String serviceUrl;
    private static ClientAndServer mockServer;

    @Autowired
    BookClientHttpInterface client;

    @BeforeAll
    static void startServer() throws Exception {
        serverPort = getFreePort();
        serviceUrl = "http://" + SERVER_ADDRESS + ":" + serverPort;

        Configuration config = Configuration.configuration().logLevel(WARN);
        mockServer = startClientAndServer(config, serverPort);

        mockAllBooksRequest();
        mockBookByIdRequest();
        mockSaveBookRequest();
        mockDeleteBookRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @Test
    @DisplayName("given mocked GET response, when getBooks service method is called for existing books, then two books are returned")
    void givenMockedGetResponse_WhenGetBookServiceMethodIsCalled_ThenTwoBooksAreReturned() {

        // given
        BookClient bookClient = new BookClient(WebClient.builder().baseUrl(serviceUrl).build());

        // when
        List<Book> books = bookClient.getBookClient().getBooks();

        // then
        assertThat(books).hasSize(2);

        // verify
        mockServer.verify(request().withMethod(GET.name()).withPath(PATH), VerificationTimes.exactly(1));
    }

    @Test
    @DisplayName("given mocked GET response, when getBook service method is called for existing book, then the correct book is returned")
    void givenMockedGetResponse_WhenGetBookServiceMethodIsCalled_ThenCorrectBookIsReturned() {

        // given
        BookClient bookClient = new BookClient(WebClient.builder().baseUrl(serviceUrl).build());

        // when
        Book book = bookClient.getBookClient().getBook(1);

        // then
        assertThat(book.id()).isEqualTo(1L);
        assertThat(book.title()).isEqualTo("Book_1");
        assertThat(book.author()).isEqualTo("Author_1");
        assertThat(book.year()).isEqualTo(1998);

        // verify
        mockServer.verify(
                request().withMethod(GET.name()).withPath(PATH + "/1"),
                VerificationTimes.exactly(1));
    }

    @Test
    @DisplayName("given mocked GET response, when getBook method is called for non-existing book, then throws WebClientResponseException")
    void givenMockedGetResponse_WhenGetNonExistingBook_ThenThrowsWebClientResponseException() {

        // given
        BookClient bookClient = new BookClient(WebClient.builder().baseUrl(serviceUrl).build());

        // when

        // then
        assertThrows(WebClientResponseException.class, () -> bookClient.getBookClient().getBook(9));
    }

    @Test
    @DisplayName("given custom error handler, when getBook method is called for non-existing book, then throws custom exception")
    void givenCustomErrorHandler_WhenGetNonExistingBook_ThenThrowsCustomException() {

        // given
        WebClient webClient = WebClient
                .builder()
                .baseUrl(serviceUrl)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, resp -> Mono.just(new MyClientException("Custom client exception")))
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, resp -> Mono.just(new MyServerException("Custom server exception")))
                .build();

        BookClient bookClient = new BookClient(webClient);

        // when

        // then
        assertThrows(MyClientException.class, () -> bookClient.getBookClient().getBook(9));
    }

    @Test
    @DisplayName("given mocked POST response, when saveBook method is called, then correct book is saved and returned")
    void giveMockedPostResponse_WhenSaveBookServiceMethodIsCalled_ThenCorrectBookIsReturned() {

        // given
        BookClient bookClient = new BookClient(WebClient.builder().baseUrl(serviceUrl).build());

        // when
        Book book = bookClient.getBookClient().saveBook(new Book(3, "Book_3", "Author_3", 2000));

        // then
        assertThat(book.id()).isEqualTo(3L);
        assertThat(book.title()).isEqualTo("Book_3");
        assertThat(book.author()).isEqualTo("Author_3");
        assertThat(book.year()).isEqualTo(2000);

        // verify
        mockServer.verify(request().withMethod(POST.name()).withPath(PATH), VerificationTimes.exactly(1));
    }

    @Test
    @DisplayName("given mocked delete response, when deleteBook method is called, then correct book is deleted and returned")
    void givenMockedDeleteResponse_WhenDeleteBookServiceMethodIsCalled_ThenCorrectBookIsReturned() {

        // given
        BookClient bookClient = new BookClient(WebClient.builder().baseUrl(serviceUrl).build());

        // when
        ResponseEntity<Void> response = bookClient.getBookClient().deleteBook(3L);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        // verify
        mockServer.verify(request().withMethod(DELETE.name()).withPath(PATH + "/3"), VerificationTimes.exactly(1));
    }

    private static void mockDeleteBookRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(request().withPath(PATH + "/3").withMethod(DELETE.name()), exactly(1))
                .respond(response().withStatusCode(SC_OK));
    }

    private static void mockSaveBookRequest() {

        String requestBody = "{\"id\":3,\"title\":\"Book_3\",\"author\":\"Author_3\",\"year\":2000}";
        String responseBody = "{\"id\":3,\"title\":\"Book_3\",\"author\":\"Author_3\",\"year\":2000}";

        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(request().withPath(PATH).withMethod(HttpMethod.POST.name()).withContentType(MediaType.APPLICATION_JSON).withBody(requestBody), exactly(1))
                .respond(response().withStatusCode(HttpStatus.SC_OK).withContentType(MediaType.APPLICATION_JSON).withBody(responseBody));
    }

    private static void mockBookByIdRequest() {

        String responseBody = "{\"id\":1,\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998}";

        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(request().withPath(PATH + "/1").withMethod(GET.name()), exactly(1))
                .respond(response()
                        .withStatusCode(SC_OK)
                        .withContentType(APPLICATION_JSON)
                        .withBody(responseBody));
    }

    private static void mockAllBooksRequest() {

        String respBody
                = "[{\"id\":1,\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998},{\"id\":2,\"title\":\"Book_2\",\"author\":\"Author_2\",\"year\":1999}]";

        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(request().withPath(PATH).withMethod(HttpMethod.GET.name()), exactly(1))
                .respond(response()
                        .withStatusCode(SC_OK)
                        .withContentType(APPLICATION_JSON)
                        .withBody(respBody));
    }

    private static int getFreePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }
}
