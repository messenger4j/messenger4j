package com.github.messenger4j.v3;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public class PersistentMenu {


    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private PersistentMenuConfiguration defaultPersistentMenuConfiguration;

        public Builder defaultConfiguration(PersistentMenuConfiguration persistentMenuConfiguration) {
            this.defaultPersistentMenuConfiguration = persistentMenuConfiguration;
            return this;
        }

        public Builder addConfiguration(SupportedLocale locale, PersistentMenuConfiguration persistentMenuConfiguration) {
            return this;
        }

        public Builder addConfiguration(String locale, PersistentMenuConfiguration persistentMenuConfiguration) {
            return this;
        }

        public PersistentMenu build() {
            return new PersistentMenu();
        }
    }
}
