package com.amatkivskiy.gitter.sdk.model.response.ban;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

public class BanResponse {
  public final UserResponse user;
  public final UserResponse bannedBy;
  public final String dateBanned;

  public BanResponse(UserResponse user, UserResponse bannedBy, String dateBanned) {
    this.user = user;
    this.bannedBy = bannedBy;
    this.dateBanned = dateBanned;
  }
}
