package com.amatkivskiy.gitter.sdk.async.faye.interfaces;

import com.google.gson.JsonObject;

import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;

import java.util.List;

/**
 * Base interface for working with Faye channels. It provides callback methods.
 * For available channels list visit <a href="https://developer.gitter.im/docs/faye-endpoint#Endpoints">Gitter Faye</a>
 */
public interface ChannelListener {

  /**
   * Callback which is called when Faye server publishes new event.
   *
   * @param channel name of the channel that received event.
   * @param message Raw event JSON.
   */
  void onMessage(String channel, JsonObject message);

  /**
   * Called when something went wrong in this channel.
   *
   * @param channel name of the channel.
   * @param ex      error.
   */
  void onFailed(String channel, Exception ex);

  /**
   * Called when channel is subscribed to the event on the Faye server.
   *
   * @param channel name of the channel.
   * @param messagesSnapshot snapshot of the last messages in the channel.
   */
  void onSubscribed(String channel, List<MessageResponse> messagesSnapshot);

  /**
   * Called when channel is unsubscribed from the Faye server.
   *
   * @param channel name of the channel.
   */
  void onUnSubscribed(String channel);
}