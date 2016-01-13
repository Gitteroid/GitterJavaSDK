package com.amatkivskiy.gitter.sdk.async.faye.interfaces;

import com.google.gson.JsonObject;

/**
 * Base interface for working with Faye channels. It provides callback methods.
 */
public interface MessageListener {

    void onMessage(String channel, JsonObject message);

    void onFailed(String channel, Exception ex);

    void onSubscribed(String channel);

    void onUnSubscribed(String channel);
  }