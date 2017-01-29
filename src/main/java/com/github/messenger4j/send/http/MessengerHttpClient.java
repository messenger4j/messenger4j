package com.github.messenger4j.send.http;

import java.io.IOException;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public interface MessengerHttpClient {

    enum Method{
        POST,
        DELETE
    }

    Response execute(String url, String jsonBody, Method method) throws IOException;

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    final class Response {

        private final int statusCode;
        private final String body;

        public Response(int statusCode, String body) {
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