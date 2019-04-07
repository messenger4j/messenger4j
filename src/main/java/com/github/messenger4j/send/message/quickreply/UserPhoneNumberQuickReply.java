package com.github.messenger4j.send.message.quickreply;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Joe Tindale
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserPhoneNumberQuickReply extends QuickReply {

  private UserPhoneNumberQuickReply() {
    super(ContentType.USER_PHONE_NUMBER);
  }

  public static UserPhoneNumberQuickReply create() {
    return new UserPhoneNumberQuickReply();
  }
}
