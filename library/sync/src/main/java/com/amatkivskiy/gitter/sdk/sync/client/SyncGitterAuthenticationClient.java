package com.amatkivskiy.gitter.sdk.sync.client;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.BaseApiBuilder;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentialsProvider;
import com.amatkivskiy.gitter.sdk.model.response.AccessTokenResponse;
import com.amatkivskiy.gitter.sdk.sync.api.SyncGitterAuthenticateApi;

import static com.amatkivskiy.gitter.sdk.Constants.GitterEndpoints.GITTER_AUTHENTICATION_ENDPOINT;

public class SyncGitterAuthenticationClient {
  private SyncGitterAuthenticateApi api;

  private SyncGitterAuthenticationClient(SyncGitterAuthenticateApi api) {
    this.api = api;
  }

  public AccessTokenResponse getAccessToken(String code) {
    GitterDeveloperCredentialsProvider provider = GitterDeveloperCredentials.getInstance().getProvider();

    return api.getAccessToken(
        provider.getOauthKey(),
        provider.getOauthSecret(),
        code,
        provider.getRedirectUrl(),
        Constants.GitterOauth.OAUTH_GRANT_TYPE_PARAMETER);
  }

  public AccessTokenResponse getAccessToken(String clientId,
                                            String clientSecret,
                                            String code,
                                            String redirectUri,
                                            String grantType) {

    return api.getAccessToken(
        clientId,
        clientSecret,
        code,
        redirectUri,
        grantType);
  }

  public static class Builder extends BaseApiBuilder<Builder, SyncGitterAuthenticationClient> {

    @Override
    public SyncGitterAuthenticationClient build() {
      restAdapterBuilder.setEndpoint(GITTER_AUTHENTICATION_ENDPOINT);
      restAdapterBuilder.setErrorHandler(gitterWrappedErrorhandler);
      SyncGitterAuthenticateApi api = restAdapterBuilder.build().create(SyncGitterAuthenticateApi.class);

      return new SyncGitterAuthenticationClient(api);
    }
  }
}
