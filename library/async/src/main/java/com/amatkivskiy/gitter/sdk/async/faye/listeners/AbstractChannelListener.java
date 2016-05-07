package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.amatkivskiy.gitter.sdk.async.faye.interfaces.ChannelListener;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;

import java.util.List;

public abstract class AbstractChannelListener<T> implements ChannelListener {
  private Gson gson;

  public AbstractChannelListener(Gson gson) {
    this.gson = gson;
  }

  /**
   * @return Full channel name. For example: <b>/api/v1/rooms/roomId/chatMessages</b>.
   */
  public abstract String getChannel();

  /**
   * @return Class of the <b>T</b> type.
   */
  protected abstract Class<T> getParameterClass();

  /**
   * Callback which is called when Faye server publishes new event.
   *
   * @param channel name of the channel that received event.
   * @param message parsed with Gson event data.
   */
  public abstract void onMessage(String channel, T message);

  public void onMessage(String channel, JsonObject event) {
    T parseMessage = gson.fromJson(event, getParameterClass());
    onMessage(channel, parseMessage);
  }

  @Override
  public void onFailed(String channel, Exception ex) {
  }

  @Override
  public void onSubscribed(String channel, List<MessageResponse> messagesSnapshot) {

  }

  @Override
  public void onUnSubscribed(String channel) {
  }
}