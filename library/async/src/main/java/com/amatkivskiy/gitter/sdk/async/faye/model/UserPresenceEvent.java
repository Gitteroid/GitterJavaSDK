package com.amatkivskiy.gitter.sdk.async.faye.model;

import com.google.gson.annotations.SerializedName;

public class UserPresenceEvent {
  public final String notification;
  public final String userId;
  public final PresenceStatus status;

  public UserPresenceEvent(String notification, String userId, PresenceStatus status) {
    this.notification = notification;
    this.userId = userId;
    this.status = status;
  }

  public enum PresenceStatus {
    @SerializedName("in")
    In,
    @SerializedName("out")
    Out
  }
}
