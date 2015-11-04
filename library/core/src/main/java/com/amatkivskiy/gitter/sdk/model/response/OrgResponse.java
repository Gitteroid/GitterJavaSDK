package com.amatkivskiy.gitter.sdk.model.response;

import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.google.gson.annotations.SerializedName;

public class OrgResponse {
  public final String id;
  public final String name;

  @SerializedName("avatar_url")
  public final String avatarUrl;
  public final boolean premium;
  public final RoomResponse room;

  public OrgResponse(String id, String name, String avatarUrl, boolean premium, RoomResponse room) {
    this.id = id;
    this.name = name;
    this.avatarUrl = avatarUrl;
    this.premium = premium;
    this.room = room;
  }
}
