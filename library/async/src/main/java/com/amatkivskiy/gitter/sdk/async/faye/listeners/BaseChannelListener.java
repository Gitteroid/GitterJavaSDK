package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.amatkivskiy.gitter.sdk.async.faye.interfaces.MessageListener;

public abstract class BaseChannelListener<T> implements MessageListener {
  private Gson gson;

  public abstract String getChannel();
  protected abstract Class<T> getParameterClass();
  public abstract void onMessage(String channel, T message);

  public BaseChannelListener(Gson gson) {
    this.gson = gson;
  }

  public void onMessage(String channel, JsonObject event) {
    T parseMessage = gson.fromJson(event, getParameterClass());
    onMessage(channel, parseMessage);
  }

  @Override
  public void onFailed(String channel, Exception ex) {
  }

  @Override
  public void onSubscribed(String channel) {
  }

  @Override
  public void onUnSubscribed(String channel) {
  }
}
