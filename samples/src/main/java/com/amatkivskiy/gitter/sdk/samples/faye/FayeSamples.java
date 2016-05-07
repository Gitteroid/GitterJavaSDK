package com.amatkivskiy.gitter.sdk.samples.faye;

import com.google.gson.JsonObject;

import com.amatkivskiy.gitter.sdk.async.faye.client.AsyncGitterFayeClient;
import com.amatkivskiy.gitter.sdk.async.faye.client.AsyncGitterFayeClientBuilder;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.ChannelListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.ConnectionListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.DisconnectionListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.Logger;
import com.amatkivskiy.gitter.sdk.async.faye.listeners.RoomMessagesChannel;
import com.amatkivskiy.gitter.sdk.async.faye.listeners.RoomUserPresenceChannel;
import com.amatkivskiy.gitter.sdk.async.faye.listeners.RoomUsersChannel;
import com.amatkivskiy.gitter.sdk.async.faye.model.MessageEvent;
import com.amatkivskiy.gitter.sdk.async.faye.model.UserEvent;
import com.amatkivskiy.gitter.sdk.async.faye.model.UserPresenceEvent;
import com.squareup.okhttp.OkHttpClient;

public class FayeSamples {
  private static final String ACCOUNT_TOKEN = "account_token";
  private static final String ROOM_ID = "room_id";

  private static AsyncGitterFayeClient client;

  public static void main(String[] args) {
    final RoomMessagesChannel secondRoomListener = new RoomMessagesChannel(ROOM_ID) {
      @Override
      public void onMessage(String channel, MessageEvent message) {
        System.out.println(String.format("Channel {%s} : %s", channel, message.message.text));
        if ("unsubscribe".equals(message.message.text)) {
          client.unSubscribe(this);
        }
      }
    };

    final RoomUserPresenceChannel userPresenceChannel = new RoomUserPresenceChannel(ROOM_ID) {
      @Override
      public void onMessage(String channel, UserPresenceEvent message) {
        System.out.println(String.format("UserPresenceEvent { %s }: %s", message.userId, message.status));
      }
    };

    final RoomUsersChannel roomUsersChannel = new RoomUsersChannel(ROOM_ID) {
      @Override
      public void onMessage(String channel, UserEvent message) {
        System.out.println(String.format("UserEvent { %s }: %s", message.user.id, message.operation));
      }
    };

    final ChannelListener customChannelListener = new ChannelListener() {
      @Override
      public void onMessage(String channel, JsonObject message) {
//        Handle message here.
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
    };

    client = new AsyncGitterFayeClientBuilder()
        .withAccountToken(ACCOUNT_TOKEN)
        .withOnDisconnected(new DisconnectionListener() {
          @Override
          public void onDisconnected() {
            // Client has disconnected. You can reconnect it here.
          }
        })
        .withOkHttpClient(new OkHttpClient())
        .build();

    client.setLogger(new Logger() {
      @Override
      public void log(String message) {
        System.out.println(message);
      }
    });

    client.connect(new ConnectionListener() {
      @Override
      public void onConnected() {
        client.subscribe(secondRoomListener);
        client.subscribe(userPresenceChannel);
        client.subscribe(roomUsersChannel);
        client.subscribe("channel_name", customChannelListener);// channel name like : /api/v1/user/:userId
      }
    });
  }
}
