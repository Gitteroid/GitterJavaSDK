package com.amatkivskiy.gitter.sdk.async.client;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.BaseApiBuilder;
import com.amatkivskiy.gitter.sdk.async.api.GitterAuthenticateApi;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentialsProvider;
import com.amatkivskiy.gitter.sdk.model.response.AccessTokenResponse;
import retrofit.Callback;

import static com.amatkivskiy.gitter.sdk.Constants.GitterEndpoints.GITTER_AUTHENTICATION_ENDPOINT;

public class GitterAuthenticationClient {
  private GitterAuthenticateApi api;

  private GitterAuthenticationClient(GitterAuthenticateApi api) {
    this.api = api;
  }

  public void getAccessToken(String code, Callback<AccessTokenResponse> callback) {
    GitterDeveloperCredentialsProvider provider = GitterDeveloperCredentials.getInstance().getProvider();

    api.getAccessToken(
        provider.getOauthKey(),
        provider.getOauthSecret(),
        code,
        provider.getRedirectUrl(),
        Constants.GitterOauth.OAUTH_GRANT_TYPE_PARAMETER,
        callback);
  }

  public void getAccessToken(String clientId,
                             String clientSecret,
                             String code,
                             String redirectUri,
                             String grantType,
                             Callback<AccessTokenResponse> callback) {

    api.getAccessToken(
        clientId,
        clientSecret,
        code,
        redirectUri,
        grantType,
        callback);
  }

  public static class Builder extends BaseApiBuilder<Builder, GitterAuthenticationClient> {

    @Override
    public GitterAuthenticationClient build() {
      restAdapterBuilder.setEndpoint(GITTER_AUTHENTICATION_ENDPOINT);
      GitterAuthenticateApi api = restAdapterBuilder.build().create(GitterAuthenticateApi.class);

      return new GitterAuthenticationClient(api);
    }
  }
}
