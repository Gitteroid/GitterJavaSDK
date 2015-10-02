package amatkivskiy.gitter.rx.sdk;

import amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentials;
import amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentialsProvider;

import static amatkivskiy.gitter.rx.sdk.Constants.GitterOauth.OAUTH_URL;
import static amatkivskiy.gitter.rx.sdk.Constants.GitterOauth.OAUTH_URL_PARAMS_FORMAT;
import static amatkivskiy.gitter.rx.sdk.Constants.GitterOauth.OAUTH_CODE_PARAMETER;

public class GitterOauthUtils {

  public static String buildOauthUrl() {
    GitterDeveloperCredentialsProvider provider = GitterDeveloperCredentials.getInstance()
        .getProvider();

    return String.format(OAUTH_URL_PARAMS_FORMAT,
                               OAUTH_URL,
                               provider.getOauthKey(),
                               OAUTH_CODE_PARAMETER,
                               provider.getRedirectUrl());
  }
}
