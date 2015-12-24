package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.amatkivskiy.gitter.sdk.async.faye.interfaces.MessageListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class BaseChannelListener<T> implements MessageListener {
  private Gson gson;

  public BaseChannelListener(Gson gson) {
    this.gson = gson;
  }

  public abstract String getChannel();
  protected abstract Class<T> getParameterClass();
  public abstract void onMessage(String channel, T message);

  public void onMessage(String channel, JsonObject message) {
    T parseMessage = gson.fromJson(message, getParameterClass());
    onMessage(channel, parseMessage);
  }

  @Override
  public void onFailed(String channel, Exception ex) {
  }

  @Override
  public void onSubscribed(String channel) {
  }
}
