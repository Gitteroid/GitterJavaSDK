package com.amatkivskiy.gitter.sdk.samples.faye;

import com.amatkivskiy.gitter.sdk.async.faye.client.AsyncGitterFayeClient;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.ConnectionListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.FailListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.Logger;
import com.amatkivskiy.gitter.sdk.async.faye.listeners.ChannelMessageListener;
import com.amatkivskiy.gitter.sdk.async.faye.model.MessageEvent;

public class FayeMain {
  public static AsyncGitterFayeClient client;

  public static void main(String[] args) {
    final ChannelMessageListener firstRoomListener = new ChannelMessageListener("5653257916b6c7089cbbd460") {
      @Override
      public void onMessage(String channel, MessageEvent message) {
        System.out.println("Room  {" + getRoomId() + "}: Received parsed message: " + message.message.text);
        if (message.message.text.equals("unsubscribe")) {
          client.unSubscribe(channel);
        }
      }

      @Override
      public void onSubscribed(String channel) {
        super.onSubscribed(channel);
      }

      @Override
      public void onUnSubscribed(String channel) {
        System.out.println("FayeMain.onUnSubscribed");
      }
    };

    final ChannelMessageListener secondRoomListener = new ChannelMessageListener("5667f73316b6c7089cbe05c0") {
      @Override
      public void onMessage(String channel, MessageEvent message) {
        System.out.println("Room  {" + getRoomId() + "}: Received parsed message: " + message.message.text);
        if (message.message.text.equals("disconnect")) {
          client.disconnect();
        }
      }

      @Override
      public void onSubscribed(String channel) {
      }

      @Override
      public void onUnSubscribed(String channel) {
        System.out.println("FayeMain.onUnSubscribed");
      }
    };

    client = new AsyncGitterFayeClient("c2fc54dc5bc835317b7b29f389383611d8940987", new FailListener() {
      @Override
      public void onFailed(Exception ex) {
        ex.printStackTrace();
        System.err.println("Error handled: " + ex);
      }
    });

    client.setPingInterval(10);
    client.setLogger(new Logger() {
      @Override
      public void log(String message) {
        System.out.println(message);
      }
    });

    client.connect(new ConnectionListener() {
      @Override
      public void onConnected() {
        client.subscribe(firstRoomListener);
        client.subscribe(secondRoomListener);
      }

      @Override
      public void onFailed(Exception ex) {

      }
    });
  }
}
