package com.amatkivskiy.gitter.sdk.async.faye.model;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

public class Context {
  public final UserResponse user;

  public Context(UserResponse user) {
    this.user = user;
  }
}


