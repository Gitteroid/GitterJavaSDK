package com.amatkivskiy.gitter.sdk.async.faye.interfaces;

import com.google.gson.JsonObject;

public interface MessageListener {

    void onMessage(String channel, JsonObject message);

    void onFailed(String channel, Exception ex);

    void onSubscribed(String channel);

    void onUnSubscribed(String channel);
  }