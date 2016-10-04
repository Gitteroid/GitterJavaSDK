package com.amatkivskiy.gitter.sdk.model.response.group;

public class GroupResponse {
  public final String id;
  public final String name;
  public final String uri;
  public final BackedBy backedBy;
  public final String avatarUrl;

  public GroupResponse(String id, String name, String uri, BackedBy backedBy, String avatarUrl) {
    this.id = id;
    this.name = name;
    this.uri = uri;
    this.backedBy = backedBy;
    this.avatarUrl = avatarUrl;
  }
}
