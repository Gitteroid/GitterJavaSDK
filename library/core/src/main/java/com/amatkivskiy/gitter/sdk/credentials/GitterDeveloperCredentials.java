package com.amatkivskiy.gitter.sdk.credentials;

public class GitterDeveloperCredentials {
  private static GitterDeveloperCredentials gitterDeveloperCredentials;
  private GitterDeveloperCredentialsProvider provider;

  public static void init(GitterDeveloperCredentialsProvider provider) {
    getInstance().provider = provider;
  }

  public static GitterDeveloperCredentials getInstance() {
    if (gitterDeveloperCredentials == null) {
      gitterDeveloperCredentials = new GitterDeveloperCredentials();
    }
    return gitterDeveloperCredentials;
  }

  private GitterDeveloperCredentials() {
  }

  public GitterDeveloperCredentialsProvider getProvider() {
    if (provider == null) {
      throw new IllegalStateException("Need to call GitterDeveloperCredentials.init() before getting provider.");
    }

    return provider;
  }
}