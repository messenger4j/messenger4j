package com.github.messenger4j.send;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.internal.PreConditions;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class MessengerSendClientBuilder {

    final String pageAccessToken;
    MessengerHttpClient httpClient;

    public MessengerSendClientBuilder(String pageAccessToken) {
        PreConditions.notNullOrBlank(pageAccessToken, "pageAccessToken");
        this.pageAccessToken = pageAccessToken;
    }

    public MessengerSendClientBuilder httpClient(MessengerHttpClient messengerHttpClient) {
        this.httpClient = messengerHttpClient;
        return this;
    }

    public MessengerSendClient build() {
        return new MessengerSendClientImpl(this);
    }
}