package com.github.messenger4j.common;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class DefaultMessengerHttpClient implements MessengerHttpClient {

    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    private final OkHttpClient okHttp = new OkHttpClient();

    @Override
    public HttpResponse execute(String url, String jsonBody, HttpMethod httpMethod) throws IOException {
        final MediaType jsonMediaType = MediaType.parse(APPLICATION_JSON_CHARSET_UTF_8);
        final RequestBody body = RequestBody.create(jsonMediaType, jsonBody);
        final Request request = new Request.Builder().url(url).method(httpMethod.name(), body).build();
        try (Response response = this.okHttp.newCall(request).execute()) {
            return new HttpResponse(response.code(), response.body().string());
        }
    }
}