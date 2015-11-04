package com.amatkivskiy.gitter.sdk.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnreadRequestParam {
  @SerializedName("chat")
  public final List<String> chatIds;

  public UnreadRequestParam(List<String> chatIds) {
    this.chatIds = chatIds;
  }
}
