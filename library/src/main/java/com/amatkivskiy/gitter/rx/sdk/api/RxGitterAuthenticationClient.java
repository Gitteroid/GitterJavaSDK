package com.amatkivskiy.gitter.rx.sdk.api;

import com.amatkivskiy.gitter.rx.sdk.Constants;
import com.amatkivskiy.gitter.rx.sdk.api.builder.BaseApiBuilder;
import com.amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentialsProvider;
import com.amatkivskiy.gitter.rx.sdk.model.response.AccessTokenResponse;
import rx.Observable;

import static com.amatkivskiy.gitter.rx.sdk.Constants.GitterEndpoints.GITTER_AUTHENTICATION_ENDPOINT;

public class RxGitterAuthenticationClient {
  private RxGitterAuthenticateApi api;

  private RxGitterAuthenticationClient(RxGitterAuthenticateApi api) {
    this.api = api;
  }

  public Observable<AccessTokenResponse> getAccessToken(String code) {
    GitterDeveloperCredentialsProvider provider = GitterDeveloperCredentials.getInstance().getProvider();

    return api.getAccessToken(
        provider.getOauthKey(),
        provider.getOauthSecret(),
        code,
        provider.getRedirectUrl(),
        Constants.GitterOauth.OAUTH_GRANT_TYPE_PARAMETER);
  }

  public Observable<AccessTokenResponse> getAccessToken(String clientId,
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

  public static class Builder extends BaseApiBuilder<Builder, RxGitterAuthenticationClient> {

    @Override
    public RxGitterAuthenticationClient build() {
      restAdapterBuilder.setEndpoint(GITTER_AUTHENTICATION_ENDPOINT);
      RxGitterAuthenticateApi api = restAdapterBuilder.build().create(RxGitterAuthenticateApi.class);

      return new RxGitterAuthenticationClient(api);
    }
  }
}
