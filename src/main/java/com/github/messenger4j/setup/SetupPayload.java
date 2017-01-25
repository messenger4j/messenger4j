package com.github.messenger4j.setup;

import com.github.messenger4j.internal.PreConditions;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Created by andrey on 23.01.17.
 */
public class SetupPayload {

    @SerializedName("setting_type")
    private SettingType settingType;

    @SerializedName("thread_state")
    private ThreadState threadState;

    @SerializedName("call_to_actions")
    private List<CallToAction> callToActions;

    private Greeting greeting;

    static SetupPayload.Builder newBuilder() {
        return new SetupPayload.Builder();
    }


    private SetupPayload(SetupPayload.Builder builder) {
        settingType = builder.settingType;
        threadState = builder.threadState;
        callToActions = builder.callToActions;
        greeting = builder.greeting;
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
            callToActions = callToActions == null ? callToActions = new ArrayList<>() : callToActions;
            callToActions.addAll(menuItems);
            return this;
        }

        public SetupPayload build() {
            PreConditions.notNull(settingType, "settingType");

            return new SetupPayload(this);
        }
    }
}
