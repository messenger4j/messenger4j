package com.github.messenger4j.receive.events;

import com.github.messenger4j.receive.handlers.EventHandler;
import java.util.Objects;

/**
 * This abstract class is the base implementation of the specific event types.
 *
 * <p>
 * Events could be user-actions (sending messages, tapping quick replies, ...) or could be created by the
 * Messenger Platform (message read, message delivered, ...).
 * </p>
 *
 * <p>
 * Events are handled by their corresponding event-specific {@link EventHandler}.
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandler
 */
public abstract class Event {

    private final Entity sender;
    private final Entity recipient;

    Event(String senderId, String recipientId) {
        this.sender = new Entity(senderId);
        this.recipient = new Entity(recipientId);
    }

    public Entity getSender() {
        return sender;
    }

    public Entity getRecipient() {
        return recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(sender, event.sender) &&
                Objects.equals(recipient, event.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient);
    }

    @Override
    public String toString() {
        return "Event{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                '}';
    }

    /**
     * An entity representing either a {@code Sender} or a {@code Recipient}.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Entity {

        private final String id;

        public Entity(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entity entity = (Entity) o;
            return Objects.equals(id, entity.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Entity{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}