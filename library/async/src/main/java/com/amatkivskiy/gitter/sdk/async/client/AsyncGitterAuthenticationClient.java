package com.amatkivskiy.gitter.sdk.async.client;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.BaseApiBuilder;
import com.amatkivskiy.gitter.sdk.async.api.AsyncGitterAuthenticateApi;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentialsProvider;
import com.amatkivskiy.gitter.sdk.model.error.GitterApiErrorResponse;
import com.amatkivskiy.gitter.sdk.model.error.GitterApiException;
import com.amatkivskiy.gitter.sdk.model.response.AccessTokenResponse;
import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;

import static com.amatkivskiy.gitter.sdk.Constants.GitterEndpoints.GITTER_AUTHENTICATION_ENDPOINT;

public class AsyncGitterAuthenticationClient {
  private AsyncGitterAuthenticateApi api;

  private AsyncGitterAuthenticationClient(AsyncGitterAuthenticateApi api) {
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

  public static class Builder extends BaseApiBuilder<Builder, AsyncGitterAuthenticationClient> {

    @Override
    public AsyncGitterAuthenticationClient build() {
      restAdapterBuilder.setEndpoint(GITTER_AUTHENTICATION_ENDPOINT);
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

      AsyncGitterAuthenticateApi api = restAdapterBuilder.build().create(AsyncGitterAuthenticateApi.class);
      return new AsyncGitterAuthenticationClient(api);
    }
  }
}
