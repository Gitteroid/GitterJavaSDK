package com.amatkivskiy.gitter.sdk.model.response.room;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoomResponse {
  @SerializedName("id") public final String id;
  @SerializedName("name") public final String name;
  @SerializedName("topic") public final String topic;
  @SerializedName("uri") public final String uri;
  @SerializedName("oneToOne") public final boolean oneToOne;
  @SerializedName("userCount") public final int userCount;
  @SerializedName("unreadItems") public final int unreadItems;
  @SerializedName("mentions") public final int mentions;
  @SerializedName("lastAccessTime") public final String lastAccessTime;
  @SerializedName("lurk") public final boolean lurk;
  @SerializedName("url") public final String url;
  @SerializedName("githubType") public final RoomType githubRoomType;
  @SerializedName("security") public final String security;
  @SerializedName("noindex") public final boolean noIndex;
  @SerializedName("tags") public final List<String> tags = new ArrayList<String>();
  @SerializedName("v") public final int v;
  @SerializedName("favourite") public final int favourite;

  public RoomResponse(String id,
                      String name,
                      String topic,
                      String uri,
                      boolean oneToOne,
                      int userCount,
                      int unreadItems,
                      int mentions,
                      String lastAccessTime,
                      boolean lurk,
                      String url,
                      RoomType githubRoomType,
                      String security,
                      boolean noIndex,
                      int v,
                      int favourite) {
    this.id = id;
    this.name = name;
    this.topic = topic;
    this.uri = uri;
    this.oneToOne = oneToOne;
    this.userCount = userCount;
    this.unreadItems = unreadItems;
    this.mentions = mentions;
    this.lastAccessTime = lastAccessTime;
    this.lurk = lurk;
    this.url = url;
    this.githubRoomType = githubRoomType;
    this.security = security;
    this.noIndex = noIndex;
    this.v = v;
    this.favourite = favourite;
  }
}
