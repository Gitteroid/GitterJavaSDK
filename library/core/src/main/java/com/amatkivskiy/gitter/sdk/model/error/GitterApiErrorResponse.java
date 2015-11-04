package com.amatkivskiy.gitter.sdk.model.error;

import com.google.gson.annotations.SerializedName;

public class GitterApiErrorResponse {
  public final String error;
  @SerializedName("error_description")
  public final String errorDescription;

  public GitterApiErrorResponse(String error, String errorDescription) {
    this.error = error;
    this.errorDescription = errorDescription;
  }
}
