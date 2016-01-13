package com.amatkivskiy.gitter.sdk.async.faye.model;

import com.google.gson.annotations.SerializedName;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

public class UserEvent {
  /**
   * Operation that was done on this user, such as: create or remove.
   */
  public final String operation;
  @SerializedName("model")
  public final UserResponse user;

  public UserEvent(String operation, UserResponse user) {
    this.operation = operation;
    this.user = user;
  }
}
