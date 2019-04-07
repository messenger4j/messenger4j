package com.github.messenger4j.messengerprofile.targetaudience;

import com.github.messenger4j.common.SupportedCountry;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class WhitelistTargetAudience extends TargetAudience {

  private final List<SupportedCountry> countries;

  private WhitelistTargetAudience(List<SupportedCountry> countries) {
    super(Type.CUSTOM);
    this.countries = countries;
  }

  public static WhitelistTargetAudience create(@NonNull List<SupportedCountry> countries) {
    return new WhitelistTargetAudience(countries);
  }

  public List<SupportedCountry> countries() {
    return countries;
  }
}
