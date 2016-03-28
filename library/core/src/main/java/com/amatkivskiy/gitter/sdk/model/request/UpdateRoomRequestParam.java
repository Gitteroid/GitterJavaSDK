package com.amatkivskiy.gitter.sdk.model.request;

import com.google.gson.annotations.SerializedName;

public class UpdateRoomRequestParam {
  @SerializedName("topic") public final String topic;
  @SerializedName("noindex") public final Boolean noIndex;
  @SerializedName("tags") public final String tags;

  /**
   * Parameters required for the update room request.
   * @see <a href="\https://developer.gitter.im/docs/rooms-resource#update-room">Room schema Gitter Doc</a>
   * @param topic Room topic.
   * @param noIndex Whether the room is indexed by search engines.
   * @param tags Tags that define the room. Values should be coma separated like "tag1, tag2, tag3".
   */
  public UpdateRoomRequestParam(String topic, Boolean noIndex, String tags) {
    this.topic = topic;
    this.noIndex = noIndex;
    this.tags = tags;
  }
}
