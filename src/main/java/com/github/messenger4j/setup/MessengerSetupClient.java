package com.github.messenger4j.setup;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.MessengerResponse;
import com.github.messenger4j.send.Recipient;

import java.util.List;

/**
 * Created by andrey on 23.01.17.
 */
public interface MessengerSetupClient {

    //Allowed constants:
    // {{user_first_name}}
    // {{user_last_name}}
    // {{user_full_name}}
    SetupResponse setupWelcomeMessage(String greeting) throws MessengerApiException, MessengerIOException;

    SetupResponse resetWelcomeMessage() throws MessengerApiException, MessengerIOException;

    SetupResponse setupStartButton(String startPayload) throws MessengerApiException, MessengerIOException;

    SetupResponse removeStartButton() throws MessengerApiException, MessengerIOException;

    SetupResponse setupPersistentMenu(List<CallToAction> menuItems) throws MessengerApiException, MessengerIOException;

    SetupResponse removePersistentMenu() throws MessengerApiException, MessengerIOException;

}
