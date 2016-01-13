package com.amatkivskiy.gitter.sdk.async.faye.interfaces;

import com.google.gson.JsonObject;

/**
 * Base interface for working with Faye channels. It provides callback methods.
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
   */
  void onSubscribed(String channel);

  /**
   * Called when channel is unsubscribed from the Faye server.
   *
   * @param channel name of the channel.
   */
  void onUnSubscribed(String channel);
}