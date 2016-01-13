package com.amatkivskiy.gitter.sdk.async.faye.model;

import com.google.gson.annotations.SerializedName;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

public class UserEvent {
  public final String operation;
  @SerializedName("model")
  public final UserResponse user;

  public UserEvent(String operation, UserResponse user) {
    this.operation = operation;
    this.user = user;
  }
}
