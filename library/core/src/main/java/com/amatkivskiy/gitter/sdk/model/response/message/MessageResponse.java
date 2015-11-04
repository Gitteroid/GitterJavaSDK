package com.amatkivskiy.gitter.sdk.model.response.message;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.Issue;
import com.amatkivskiy.gitter.sdk.model.response.room.Mention;
import com.amatkivskiy.gitter.sdk.model.response.room.Meta;
import com.amatkivskiy.gitter.sdk.model.response.room.Url;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageResponse {
  public final String id;
  public final String text;
  public final String html;
  public final String sent;
  public final String editedAt;
  public final UserResponse fromUser;

  @SerializedName("unread")
  public final boolean unRead;
  public final int readBy;
  public final List<Url> urls;
  public final List<Mention> mentions;
  public final List<Issue> issues;
  public final List<Meta> meta;

  @SerializedName("v")
  public final int version;

  public MessageResponse(String id,
                         String text,
                         String html,
                         String sent,
                         String editedAt,
                         UserResponse fromUser,
                         boolean unRead,
                         int readBy,
                         List<Url> urls,
                         List<Mention> mentions,
                         List<Issue> issues,
                         List<Meta> meta,
                         int version) {
    this.id = id;
    this.text = text;
    this.html = html;
    this.sent = sent;
    this.editedAt = editedAt;
    this.fromUser = fromUser;
    this.unRead = unRead;
    this.readBy = readBy;
    this.urls = urls;
    this.mentions = mentions;
    this.issues = issues;
    this.meta = meta;
    this.version = version;
  }
}
