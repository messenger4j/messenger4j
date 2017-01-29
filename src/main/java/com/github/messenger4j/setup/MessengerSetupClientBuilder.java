package com.github.messenger4j.setup;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.internal.PreConditions;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
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