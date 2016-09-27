package com.amatkivskiy.gitter.sdk.model.response.room;

import com.google.gson.annotations.SerializedName;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class RoomResponse {
  @SerializedName("id") public final String id;
  @SerializedName("name") public final String name;
  @SerializedName("topic") public final String topic;
  @SerializedName("avatarUrl") public final String avatarUrl;
  @SerializedName("uri") public final String uri;
  @SerializedName("oneToOne") public final boolean oneToOne;
  @SerializedName("user") public final UserResponse user;
  @SerializedName("userCount") public final int userCount;
  @SerializedName("unreadItems") public final int unreadItems;
  @SerializedName("mentions") public final int mentions;
  @SerializedName("lastAccessTime") public final String lastAccessTime;
  @SerializedName("favouriteOrder") public final int favouriteOrder;
  @SerializedName("lurk") public final boolean lurk;
  @SerializedName("url") public final String url;
  @SerializedName("githubType") public final RoomType githubRoomType;
  @SerializedName("security") public final String security;
  @SerializedName("noindex") public final boolean noIndex;
  @SerializedName("tags") public final List<String> tags = new ArrayList<String>();
  @SerializedName("v") public final int v;
  @SerializedName("roomMember") public final boolean isRoomMember;

  public RoomResponse(String id,
                      String name,
                      String topic,
                      String avatarUrl,
                      String uri,
                      boolean oneToOne,
                      UserResponse user,
                      int userCount,
                      int unreadItems,
                      int mentions,
                      String lastAccessTime,
                      int favouriteOrder,
                      boolean lurk,
                      String url,
                      RoomType githubRoomType,
                      String security,
                      boolean noIndex,
                      int v,
                      boolean isRoomMember) {
    this.id = id;
    this.name = name;
    this.topic = topic;
    this.avatarUrl = avatarUrl;
    this.uri = uri;
    this.oneToOne = oneToOne;
    this.user = user;
    this.userCount = userCount;
    this.unreadItems = unreadItems;
    this.mentions = mentions;
    this.lastAccessTime = lastAccessTime;
    this.favouriteOrder = favouriteOrder;
    this.lurk = lurk;
    this.url = url;
    this.githubRoomType = githubRoomType;
    this.security = security;
    this.noIndex = noIndex;
    this.v = v;
    this.isRoomMember = isRoomMember;
  }

  @Override
  public String toString() {
    return "RoomResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", topic='" + topic + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", uri='" + uri + '\'' +
        ", oneToOne=" + oneToOne +
        ", user=" + user +
        ", userCount=" + userCount +
        ", unreadItems=" + unreadItems +
        ", mentions=" + mentions +
        ", lastAccessTime='" + lastAccessTime + '\'' +
        ", favouriteOrder=" + favouriteOrder +
        ", lurk=" + lurk +
        ", url='" + url + '\'' +
        ", githubRoomType=" + githubRoomType +
        ", security='" + security + '\'' +
        ", noIndex=" + noIndex +
        ", tags=" + tags +
        ", v=" + v +
        ", isRoomMember=" + isRoomMember +
        '}';
  }
}
