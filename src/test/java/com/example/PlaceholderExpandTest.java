package com.example;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static io.micronaut.http.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class PlaceholderExpandTest {

    public static final String SWAGGER_PATH = "/swagger";

    @Inject
    @Client("/")
    HttpClient client;

    @Value(value = "/${micronaut.application.name}-2.2.2.yml")
    String swaggerYamlFilename;

    @Test
    void expandsPlaceholders() {
        URI uri = UriBuilder.of(SWAGGER_PATH + swaggerYamlFilename).build();

        HttpRequest<String> request = HttpRequest.GET(uri);

        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        assertEquals(OK, response.status());

        var body = response.body();

        assertNotNull(body);

        assertAll(
                () -> assertFalse(body.contains("api.version"),
                                  "Swagger YAML does not contain placeholder 'api.verision'"),
                () -> assertFalse(body.contains("another.placeholder.value"),
                                  "Swagger YAML does not contain placeholder 'another.placeholder.value'")
        );

    }

}
