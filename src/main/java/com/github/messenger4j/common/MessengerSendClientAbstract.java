package com.github.messenger4j.common;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.http.MessengerHttpClient;
import com.github.messenger4j.setup.SetupPayload;
import com.github.messenger4j.setup.SetupResponse;
import com.google.gson.*;
import sun.plugin2.util.PojoUtil;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by andrey on 25.01.17.
 */
public abstract class MessengerSendClientAbstract<P, R> {

    private final Gson gson;
    private final JsonParser jsonParser;
    private final String requestUrl;
    private final MessengerHttpClient httpClient;

    protected MessengerSendClientAbstract(String requestUrl, MessengerHttpClient httpClient) {
        this.gson = new GsonBuilder().registerTypeAdapter(Float.class, floatSerializer()).create();
        this.jsonParser = new JsonParser();
        this.requestUrl = requestUrl;
        this.httpClient = httpClient;
    }

    protected R sendPayload(P payload, MessengerHttpClient.Method method) throws MessengerApiException, MessengerIOException {

        try {
            final String jsonBody = this.gson.toJson(payload);
            final MessengerHttpClient.Response response = this.httpClient.execute(this.requestUrl, jsonBody, method);
            final JsonObject responseJsonObject = this.jsonParser.parse(response.getBody()).getAsJsonObject();

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                return responseFromJson(responseJsonObject);
            } else {
                throw MessengerApiException.fromJson(responseJsonObject);
            }
        } catch (IOException e) {
            throw new MessengerIOException(e);
        }
    }

    protected abstract R responseFromJson(JsonObject responseJsonObject);

    private JsonSerializer<Float> floatSerializer() {
        return new JsonSerializer<Float>() {
            public JsonElement serialize(Float floatValue, java.lang.reflect.Type type, JsonSerializationContext context) {
                if (floatValue.isNaN() || floatValue.isInfinite()) {
                    return null;
                }
                return new JsonPrimitive(new BigDecimal(floatValue).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        };
    }
}
