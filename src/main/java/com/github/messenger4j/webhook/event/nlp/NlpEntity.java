package com.github.messenger4j.webhook.event.nlp;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Joe Tindale
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class NlpEntity {
    private final Gson gson = new Gson();
    private final String json;

    public NlpEntity(String json) {
        this.json = json;
    }

    public String asString() {
        return json;
    }

    public <T> T as(Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
