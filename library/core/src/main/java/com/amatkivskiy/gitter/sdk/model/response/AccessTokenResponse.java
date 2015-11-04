package com.amatkivskiy.gitter.sdk.model.response;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {
  @SerializedName("access_token")
  public final String accessToken;

  @SerializedName("token_type")
  public final String tokenType;

  public AccessTokenResponse(String accessToken, String tokenType) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
  }
}
