package com.interview.legacy.support;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ResponseWriter {
    private ResponseWriter() {
    }

    public static void writeJson(HttpExchange exchange, int statusCode, String body) throws IOException {
        write(exchange, statusCode, "application/json", body);
    }

    public static void writeText(HttpExchange exchange, int statusCode, String contentType, String body)
        throws IOException {
        write(exchange, statusCode, contentType, body);
    }

    private static void write(HttpExchange exchange, int statusCode, String contentType, String body)
        throws IOException {
        byte[] payload = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", contentType);
        exchange.sendResponseHeaders(statusCode, payload.length);
        exchange.getResponseBody().write(payload);
        exchange.getResponseBody().close();
    }
}
