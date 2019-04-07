package com.github.messenger4j.send.message.quickreply;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Joe Tindale
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserEmailQuickReply extends QuickReply {

  private UserEmailQuickReply() {
    super(ContentType.USER_EMAIL);
  }

  public static UserEmailQuickReply create() {
    return new UserEmailQuickReply();
  }
}
