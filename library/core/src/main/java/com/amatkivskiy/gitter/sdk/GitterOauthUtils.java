package com.amatkivskiy.gitter.sdk;

import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentialsProvider;

import static com.amatkivskiy.gitter.sdk.Constants.GitterOauth.OAUTH_URL;
import static com.amatkivskiy.gitter.sdk.Constants.GitterOauth.OAUTH_URL_PARAMS_FORMAT;
import static com.amatkivskiy.gitter.sdk.Constants.GitterOauth.OAUTH_CODE_PARAMETER;

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
