package amatkivskiy.gitter.rx.sdk.api;

import amatkivskiy.gitter.rx.sdk.Constants;
import amatkivskiy.gitter.rx.sdk.model.response.AccessTokenResponse;
import amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentials;
import amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentialsProvider;

import rx.Observable;

import static amatkivskiy.gitter.rx.sdk.Constants.GitterEndpoints.GITTER_AUTHENTICATION_ENDPOINT;

public class RxGitterAuthenticationClient extends BaseApiClient {

  public Observable<AccessTokenResponse> getAccessToken(String code) {
    GitterDeveloperCredentialsProvider provider = GitterDeveloperCredentials.getInstance().getProvider();

    return createApi(adapterBuilder.build(), RxGitterAuthenticateApi.class).getAccessToken(
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

    return createApi(adapterBuilder.build(), RxGitterAuthenticateApi.class).getAccessToken(
        clientId,
        clientSecret,
        code,
        redirectUri,
        grantType);
  }

  @Override
  protected String getEndpointUrl() {
    return GITTER_AUTHENTICATION_ENDPOINT;
  }

  @Override
  protected String getEndpointVersion() {
    return "";
  }
}
