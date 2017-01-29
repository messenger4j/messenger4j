package com.github.messenger4j.setup;

import static java.util.Collections.singletonList;

import com.github.messenger4j.internal.PreConditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
final class SetupPayload {

    private final SettingType settingType;

    private final ThreadState threadState;

    private final List<CallToAction> callToActions;

    private final Greeting greeting;

    static SetupPayload.Builder newBuilder() {
        return new SetupPayload.Builder();
    }

    private SetupPayload(SetupPayload.Builder builder) {
        settingType = builder.settingType;
        threadState = builder.threadState;
        callToActions = builder.callToActions == null ? null : Collections.unmodifiableList(builder.callToActions);
        greeting = builder.greeting;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public ThreadState getThreadState() {
        return threadState;
    }

    public List<CallToAction> getCallToActions() {
        return callToActions;
    }

    public Greeting getGreeting() {
        return greeting;
    }

    static final class Builder {
        private SettingType settingType;
        private ThreadState threadState;
        private List<CallToAction> callToActions;
        private Greeting greeting;

        public SetupPayload.Builder settingType(SettingType settingType) {
            this.settingType = settingType;
            return this;
        }

        public SetupPayload.Builder threadState(ThreadState threadState) {
            this.threadState = threadState;
            return this;
        }

        public SetupPayload.Builder greeting(String greeting) {
            this.greeting = new Greeting(greeting);
            return this;
        }

        public SetupPayload.Builder payload(String payload) {
            this.callToActions = singletonList(new CallToAction(payload));
            return this;
        }

        public SetupPayload.Builder addMenuItems(Collection<CallToAction> menuItems) {
            if (callToActions == null) {
                callToActions = new ArrayList<>();
            }
            callToActions.addAll(menuItems);
            return this;
        }

        public SetupPayload build() {
            PreConditions.notNull(settingType, "settingType");
            return new SetupPayload(this);
        }
    }
}
