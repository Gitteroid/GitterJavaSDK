package com.amatkivskiy.gitter.sdk.rx.streaming.model;

import com.google.gson.JsonObject;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

public class RoomEvent {
  public final String id;
  public final String text;
  public final String html;
  public final String sent;
  public final String editedAt;
  public final UserResponse fromUser;

  public final JsonObject meta;
  public final JsonObject payload;

  public final String v;

  public RoomEvent(String id, String text, String html, String sent, String editedAt, UserResponse fromUser, JsonObject meta, JsonObject payload, String v) {
    this.id = id;
    this.text = text;
    this.html = html;
    this.sent = sent;
    this.editedAt = editedAt;
    this.fromUser = fromUser;
    this.meta = meta;
    this.payload = payload;
    this.v = v;
  }
}
