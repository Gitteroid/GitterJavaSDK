package com.amatkivskiy.gitter.sdk.credentials;

public interface GitterDeveloperCredentialsProvider {
    String getOauthKey();
    String getOauthSecret();
    String getRedirectUrl();
}