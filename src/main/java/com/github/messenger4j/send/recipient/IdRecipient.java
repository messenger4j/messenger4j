package com.github.messenger4j.send.recipient;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class IdRecipient extends Recipient {

  private final String id;

  private IdRecipient(String id) {
    this.id = id;
  }

  public static IdRecipient create(@NonNull String id) {
    return new IdRecipient(id);
  }

  public String id() {
    return id;
  }
}
