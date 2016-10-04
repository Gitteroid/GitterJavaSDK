package com.amatkivskiy.gitter.sdk.rx.client;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.BaseApiBuilder;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentialsProvider;
import com.amatkivskiy.gitter.sdk.model.error.GitterApiErrorResponse;
import com.amatkivskiy.gitter.sdk.model.error.GitterApiException;
import com.amatkivskiy.gitter.sdk.model.response.AccessTokenResponse;
import com.amatkivskiy.gitter.sdk.rx.api.RxGitterAuthenticateApi;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import rx.Observable;

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
      restAdapterBuilder.setEndpoint(Constants.GitterEndpoints.GITTER_AUTHENTICATION_ENDPOINT);
      restAdapterBuilder.setErrorHandler(new ErrorHandler() {
        @Override
        public Throwable handleError(RetrofitError cause) {
          Throwable returnThrowable = cause;
          if (cause.getKind() == RetrofitError.Kind.HTTP) {
            if (cause.getResponse() != null) {
              GitterApiErrorResponse errorResponse = (GitterApiErrorResponse) cause.getBodyAs(GitterApiErrorResponse.class);

              if (errorResponse != null) {
                returnThrowable = new GitterApiException(errorResponse);
                returnThrowable.setStackTrace(cause.getStackTrace());
              }
            }
          }

          return returnThrowable;
        }
      });

      RxGitterAuthenticateApi api = restAdapterBuilder.build().create(RxGitterAuthenticateApi.class);

      return new RxGitterAuthenticationClient(api);
    }
  }
}
