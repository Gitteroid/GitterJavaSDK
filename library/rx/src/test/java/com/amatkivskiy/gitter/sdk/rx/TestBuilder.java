package com.amatkivskiy.gitter.sdk.rx;

import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;

public class TestBuilder extends RxGitterApiClient.Builder {
  private final String baseUrl;

  public TestBuilder(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  protected String getFullEndpointUrl() {
    return this.baseUrl + apiVersion + "/";
  }
}
