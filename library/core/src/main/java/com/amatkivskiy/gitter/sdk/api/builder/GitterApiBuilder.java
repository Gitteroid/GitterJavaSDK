package com.amatkivskiy.gitter.sdk.api.builder;

import retrofit.RequestInterceptor;

import static com.amatkivskiy.gitter.sdk.Constants.GitterEndpoints.GITTER_API_ENDPOINT_VERSION;
import static com.amatkivskiy.gitter.sdk.Constants.GitterRequestHeaderParams.AUTHORIZATION_REQUEST_HEADER;
import static com.amatkivskiy.gitter.sdk.Constants.GitterRequestHeaderParams.BEARER_REQUEST_HEADER;

public abstract class GitterApiBuilder<Builder, ApiClient> extends BaseApiBuilder<Builder, ApiClient> {
  protected String accountToken;
  protected String apiVersion = GITTER_API_ENDPOINT_VERSION;

  protected abstract String getFullEndpointUrl();

  @SuppressWarnings("unchecked")
  public Builder withAccountToken(String accountToken) {
    if (accountToken == null || accountToken.isEmpty()) {
      throw new IllegalArgumentException("accountToken shouldn't be null or empty");
    }

    this.accountToken = accountToken;
    return (Builder) this;
  }

  @SuppressWarnings("unchecked")
  public Builder withApiVersion(String apiVersion) {
    if (apiVersion == null || apiVersion.isEmpty()) {
      throw new IllegalArgumentException("apiVersion shouldn't be null or empty");
    }

    this.apiVersion = apiVersion;
    return (Builder) this;
  }

  protected void prepareDefaultBuilderConfig() {
    if (accountToken == null || accountToken.isEmpty()) {
      throw new IllegalStateException("You should provide proper accountToken");
    }

    restAdapterBuilder.setEndpoint(getFullEndpointUrl());

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
      @Override
      public void intercept(RequestFacade requestFacade) {
        requestFacade.addHeader(AUTHORIZATION_REQUEST_HEADER, BEARER_REQUEST_HEADER + " " + accountToken);
      }
    };
    restAdapterBuilder.setRequestInterceptor(requestInterceptor);
  }
}