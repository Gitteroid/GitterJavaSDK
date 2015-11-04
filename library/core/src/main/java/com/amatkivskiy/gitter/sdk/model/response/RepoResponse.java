package com.amatkivskiy.gitter.sdk.model.response;

import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.google.gson.annotations.SerializedName;

public class RepoResponse {
  public final String id;
  public final String name;
  public final String description;
  public final String uri;

  @SerializedName("private")
  public final boolean isPrivate;

  public final RoomResponse room;
  public final boolean exists;

  @SerializedName("avatar_url")
  public final String avatarUrl;

  public RepoResponse(String id, String name, String description, String uri, boolean isPrivate, RoomResponse room, boolean exists, String avatarUrl) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.uri = uri;
    this.isPrivate = isPrivate;
    this.room = room;
    this.exists = exists;
    this.avatarUrl = avatarUrl;
  }
}
