package com.github.messenger4j.user;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.internal.PreConditions;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
 */
public final class UserProfileClientBuilder {

    final String pageAccessToken;
    MessengerHttpClient httpClient;

    public UserProfileClientBuilder(String pageAccessToken) {
        PreConditions.notNullOrBlank(pageAccessToken, "pageAccessToken");
        this.pageAccessToken = pageAccessToken;
    }

    public UserProfileClientBuilder httpClient(MessengerHttpClient messengerHttpClient) {
        this.httpClient = messengerHttpClient;
        return this;
    }

    public UserProfileClient build() {
        return new UserProfileClientImpl(this);
    }
}