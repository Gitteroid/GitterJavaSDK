package com.amatkivskiy.gitter.sdk.async.faye.model;

public class BaseFayeResponse {
  public final String channel;
  public final String clientId;

  public BaseFayeResponse(String channel, String clientId) {
    this.channel = channel;
    this.clientId = clientId;
  }
}