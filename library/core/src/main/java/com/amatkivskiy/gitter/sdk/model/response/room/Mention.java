package com.amatkivskiy.gitter.sdk.model.response.room;

import java.util.List;

public class Mention {
  public final String screenName;
  public final String userId;
  public final List<String> userIds;

  public Mention(String screenName, String userId, List<String> userIds) {
    this.screenName = screenName;
    this.userId = userId;
    this.userIds = userIds;
  }
}
