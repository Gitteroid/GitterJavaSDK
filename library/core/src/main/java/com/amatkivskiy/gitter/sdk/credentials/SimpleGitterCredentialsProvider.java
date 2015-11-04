package com.amatkivskiy.gitter.sdk.credentials;

public class SimpleGitterCredentialsProvider implements GitterDeveloperCredentialsProvider {
  private String oauthKey;
  private String oauthSecret;
  private String redirectUrl;

  public SimpleGitterCredentialsProvider(String oauthKey, String oauthSecret, String redirectUrl) {
    this.oauthKey = oauthKey;
    this.oauthSecret = oauthSecret;
    this.redirectUrl = redirectUrl;
  }

  @Override
  public String getOauthKey() {
    return oauthKey;
  }

  @Override
  public String getOauthSecret() {
    return oauthSecret;
  }

  @Override
  public String getRedirectUrl() {
    return redirectUrl;
  }
}
