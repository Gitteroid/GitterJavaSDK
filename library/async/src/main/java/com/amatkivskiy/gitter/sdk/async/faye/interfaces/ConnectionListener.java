package com.amatkivskiy.gitter.sdk.async.faye.interfaces;

public interface ConnectionListener extends FailListener {
  void onConnected();
}