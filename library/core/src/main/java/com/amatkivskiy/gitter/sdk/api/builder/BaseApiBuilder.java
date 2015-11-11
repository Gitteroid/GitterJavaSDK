package com.amatkivskiy.gitter.sdk.api.builder;

import retrofit.Profiler;
import retrofit.RestAdapter;
import retrofit.client.Client;

import java.util.concurrent.Executor;

public abstract class BaseApiBuilder<BuilderClass, ApiClass> {
  protected RestAdapter.Builder restAdapterBuilder;

  public BaseApiBuilder() {
    restAdapterBuilder = new RestAdapter.Builder();
  }

  public abstract ApiClass build();

  @SuppressWarnings("unchecked")
  public BuilderClass withClient(Client client) {
    restAdapterBuilder.setClient(client);
    return (BuilderClass) this;
  }

  @SuppressWarnings("unchecked")
  public BuilderClass withClient(Client.Provider clientProvider) {
    restAdapterBuilder.setClient(clientProvider);
    return (BuilderClass) this;
  }

  @SuppressWarnings("unchecked")
  public BuilderClass withExecutors(Executor httpExecutor, Executor callbackExecutor) {
    restAdapterBuilder.setExecutors(httpExecutor, callbackExecutor);
    return (BuilderClass) this;
  }

  @SuppressWarnings("unchecked")
  public BuilderClass withProfiler(Profiler profiler) {
    restAdapterBuilder.setProfiler(profiler);
    return (BuilderClass) this;
  }

  @SuppressWarnings("unchecked")
  public BuilderClass withLog(RestAdapter.Log log) {
    restAdapterBuilder.setLog(log);
    return (BuilderClass) this;
  }

  @SuppressWarnings("unchecked")
  public BuilderClass withLogLevel(RestAdapter.LogLevel logLevel) {
    restAdapterBuilder.setLogLevel(logLevel);
    return (BuilderClass) this;
  }
}
