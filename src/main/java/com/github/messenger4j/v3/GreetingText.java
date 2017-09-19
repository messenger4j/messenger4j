package com.github.messenger4j.v3;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public class GreetingText {

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String defaultText;

        public Builder defaultGreeting(String text) {
            this.defaultText = text;
            return this;
        }

        public Builder addGreeting(SupportedLocale locale, String text) {
            return this;
        }

        public GreetingText build() {
            return new GreetingText();
        }
    }
}
