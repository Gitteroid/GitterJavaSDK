package com.amatkivskiy.gitter.sdk;

import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentialsProvider;

public class GitterOauthUtils {

  public static String buildOauthUrl() {
    GitterDeveloperCredentialsProvider provider = GitterDeveloperCredentials.getInstance()
        .getProvider();

    return String.format(Constants.GitterOauth.OAUTH_URL_PARAMS_FORMAT,
                               Constants.GitterOauth.OAUTH_URL,
                               provider.getOauthKey(),
                               Constants.GitterOauth.OAUTH_CODE_PARAMETER,
                               provider.getRedirectUrl());
  }
}
