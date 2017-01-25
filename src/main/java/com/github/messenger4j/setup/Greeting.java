package com.github.messenger4j.setup;

/**
 * Created by andrey on 23.01.17.
 */

public class Greeting {

    private String text;

    public Greeting(String greeting) {
        text = greeting;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
