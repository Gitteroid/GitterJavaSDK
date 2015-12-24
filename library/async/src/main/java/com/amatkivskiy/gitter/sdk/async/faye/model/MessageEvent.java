package com.amatkivskiy.gitter.sdk.async.faye.model;

import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.google.gson.annotations.SerializedName;

public class MessageEvent {
  public final String operation;

  @SerializedName("model")
  public final MessageResponse message;

  public MessageEvent(String operation, MessageResponse message) {
    this.operation = operation;
    this.message = message;
  }
}
