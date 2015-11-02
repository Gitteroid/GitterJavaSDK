package com.amatkivskiy.gitter.rx.sdk.api.usual;

import com.amatkivskiy.gitter.rx.sdk.Constants;
import com.amatkivskiy.gitter.rx.sdk.api.builder.BaseApiBuilder;
import com.amatkivskiy.gitter.rx.sdk.api.services.usual.GitterAuthenticateApi;
import com.amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentialsProvider;
import com.amatkivskiy.gitter.rx.sdk.model.response.AccessTokenResponse;
import retrofit.Callback;

import static com.amatkivskiy.gitter.rx.sdk.Constants.GitterEndpoints.GITTER_AUTHENTICATION_ENDPOINT;

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
