package com.github.messenger4j.setup;

import com.github.messenger4j.internal.PreConditions;
import com.github.messenger4j.send.http.MessengerHttpClient;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class MessengerSetupClientBuilder {

    final String pageAccessToken;
    MessengerHttpClient httpClient;

    public MessengerSetupClientBuilder(String pageAccessToken) {
        PreConditions.notNullOrBlank(pageAccessToken, "pageAccessToken");
        this.pageAccessToken = pageAccessToken;
    }

    public MessengerSetupClientBuilder httpClient(MessengerHttpClient messengerHttpClient) {
        this.httpClient = messengerHttpClient;
        return this;
    }

    public MessengerSetupClient build() {
        return new MessengerSetupClientImpl(this);
    }
}