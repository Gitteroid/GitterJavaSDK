package com.amatkivskiy.gitter.sdk.async.faye.model;

public class Ext {
  public final String appVersion;
  public final String userId;
  public final Context context;

  public Ext(String appVersion, String userId, Context context) {
    this.appVersion = appVersion;
    this.userId = userId;
    this.context = context;
  }
}
