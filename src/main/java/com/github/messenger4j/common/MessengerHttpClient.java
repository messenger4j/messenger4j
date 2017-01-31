package com.github.messenger4j.common;

import java.io.IOException;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public interface MessengerHttpClient {

    /**
     * @author Andriy Koretskyy
     * @since 0.8.0
     */
    enum HttpMethod {
        GET,
        POST,
        DELETE
    }

    /**
     * @since 0.8.0
     */
    HttpResponse execute(HttpMethod httpMethod, String url, String jsonBody) throws IOException;

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    final class HttpResponse {

        private final int statusCode;
        private final String body;

        public HttpResponse(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getBody() {
            return body;
        }
    }
}