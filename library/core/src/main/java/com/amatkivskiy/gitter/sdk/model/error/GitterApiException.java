package com.amatkivskiy.gitter.sdk.model.error;

public class GitterApiException extends Exception {
  private GitterApiErrorResponse gitterApiErrorResponse;

  public GitterApiException(GitterApiErrorResponse gitterApiErrorResponse) {
    this.gitterApiErrorResponse = gitterApiErrorResponse;
  }

  public GitterApiErrorResponse getGitterApiErrorResponse() {
    return gitterApiErrorResponse;
  }

  @Override
  public String toString() {
    return "GitterApiException{" +
        "error=\"" + gitterApiErrorResponse.error + "\", " +
        "error_description=\"" + gitterApiErrorResponse.errorDescription +
        "\"}";
  }
}
