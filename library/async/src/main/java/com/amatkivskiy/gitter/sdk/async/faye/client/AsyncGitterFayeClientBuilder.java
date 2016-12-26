package com.amatkivskiy.gitter.sdk.async.faye.client;

import com.amatkivskiy.gitter.sdk.async.faye.interfaces.DisconnectionListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.FailListener;

import okhttp3.OkHttpClient;

public class AsyncGitterFayeClientBuilder {
  private String accountToken;
  private DisconnectionListener onDisconnected;
  private FailListener failListener;
  private OkHttpClient okHttpClient;

  public AsyncGitterFayeClientBuilder withAccountToken(String accountToken) {
    this.accountToken = accountToken;
    return this;
  }

  public AsyncGitterFayeClientBuilder withOnDisconnected(DisconnectionListener onDisconnected) {
    this.onDisconnected = onDisconnected;
    return this;
  }

  public AsyncGitterFayeClientBuilder withFailListener(FailListener failListener) {
    this.failListener = failListener;
    return this;
  }

  public AsyncGitterFayeClientBuilder withOkHttpClient(OkHttpClient okHttpClient) {
    this.okHttpClient = okHttpClient;
    return this;
  }

  public AsyncGitterFayeClient build() {
    return new AsyncGitterFayeClient(accountToken, onDisconnected, failListener, okHttpClient);
  }
}