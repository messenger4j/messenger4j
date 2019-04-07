package com.github.messenger4j.messengerprofile.targetaudience;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AllTargetAudience extends TargetAudience {

  private AllTargetAudience() {
    super(Type.ALL);
  }

  public static AllTargetAudience create() {
    return new AllTargetAudience();
  }
}
