package com.roche.interview.spring.controller;

import com.mongodb.client.MongoDatabase;
import com.roche.interview.lib.repository.mongo.MongoProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// TODO establish embedded mongodb configuration for testing
public class ProductControllerTest {

    private HttpClient httpClient;
    private URI uri;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private MongoDatabase mongoDatabase;

    @BeforeEach
    void setUp() {
        mongoDatabase.getCollection(MongoProductRepository.COLLECTION_NAME).drop();

        uri = UriComponentsBuilder.fromHttpUrl(serverUrl)
            .port(serverPort)
            .pathSegment(ProductController.PATH)
            .build()
            .toUri();

        httpClient = HttpClient.newBuilder().build();
    }

    @Test
    void testSuccessfulAddition() throws IOException, InterruptedException {
        // given
        var request = postRequest(productA());

        // when
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    @Test
    void testFailedAddition() throws IOException, InterruptedException {
        // given
        var request = postRequest("invalid json");

        // when
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    void testManyAdditionsWithGettingAll() throws IOException, InterruptedException {
        // given
        var request1 = postRequest(productA());
        var request2 = postRequest(productB());
        var request3 = getRequest();

        // when
        var response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
        var response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        var response3 = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());
        var productsJson = response3.body();

        // then
        assertThat(response1.statusCode()).isEqualTo(CREATED.value());
        assertThat(response2.statusCode()).isEqualTo(CREATED.value());
        assertThat(response3.statusCode()).isEqualTo(OK.value());

        // TODO better checking + better assertions
        assertThat(productsJson).contains("\"name\":\"A\",\"price\":10.99");
        assertThat(productsJson).contains("\"name\":\"B\",\"price\":20.01");
    }

    @Test
    // TODO (more than 2 hours are needed)
    void testRemove() {
    }

    @Test
    // TODO (more than 2 hours are needed)
    void testUpdate() {

    }

    private HttpRequest postRequest(String json) {
        return HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .uri(uri)
                .build();
    }

    private HttpRequest getRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .uri(uri)
                .build();
    }

    private String productA() {
        return "{\"name\":\"A\",\"price\":\"10.99\"}";
    }

    private String productB() {
        return "{\"name\":\"B\",\"price\":\"20.01\"}";
    }
}
