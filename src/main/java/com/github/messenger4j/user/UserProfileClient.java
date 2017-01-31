package com.github.messenger4j.user;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
 */
public interface UserProfileClient {

    UserProfile queryUserProfile(String userId) throws MessengerApiException, MessengerIOException;
}
