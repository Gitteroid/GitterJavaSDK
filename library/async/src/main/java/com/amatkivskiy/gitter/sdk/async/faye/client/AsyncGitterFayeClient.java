package com.amatkivskiy.gitter.sdk.async.faye.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.amatkivskiy.gitter.sdk.async.faye.FayeConstants;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.ConnectionListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.FailListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.HandshakeListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.Logger;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.MessageListener;
import com.amatkivskiy.gitter.sdk.async.faye.listeners.BaseChannelListener;
import com.amatkivskiy.gitter.sdk.async.faye.util.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ws.WebSocket;
import com.squareup.okhttp.ws.WebSocketCall;
import com.squareup.okhttp.ws.WebSocketListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okio.Buffer;
import okio.BufferedSource;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.ApiEndpoint.GITTER_FAYE_ENDPOINT;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.*;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.*;

public class AsyncGitterFayeClient {
  public static final String VALUE_VERSION = "1.0";
  public static final String VALUE_MIN_VERSION = "1.0beta";
  public static final String VALUE_CONNECTION_TYPE = "websocket";

  private static final int DEFAULT_PING_INTERVAL_SEC = 5;

  private TimerTask pingTask;
  private Logger logger = new Logger() {
    @Override
    public void log(String message) {

    }
  };

  private String accountToken;
  private String clientId;
  private WebSocket webSocket;
  private HashMap<String, MessageListener> channelListeners = new HashMap<>();

  private HandshakeListener handshakeListener;
  private ConnectionListener connectionListener;
  private FailListener failListener = new FailListener() {
    @Override
    public void onFailed(Exception ex) {

    }
  };

  public AsyncGitterFayeClient(String accountToken) {
    this.accountToken = accountToken;
  }

  public AsyncGitterFayeClient(String accountToken, FailListener failListener) {
    this.failListener = failListener;
    this.accountToken = accountToken;
  }

  public void connect(ConnectionListener onConnected) {
    connectionListener = onConnected;
    performHandshake();
  }

  public void setLogger(Logger logger) {
    if (logger == null) {
      throw new IllegalArgumentException("Logger should not be null.");
    }

    this.logger = logger;
  }

  public void subscribe(String channel, MessageListener listener) {
    channelListeners.put(channel, listener);
    sendMessage(Utils.createChannelSubscription(this.accountToken, channel, this.clientId));
  }

  public void unSubscribe(String channel) {
    sendMessage(Utils.createChannelUnSubscription(this.accountToken, channel, this.clientId));
  }

  public void subscribe(BaseChannelListener listener) {
    subscribe(listener.getChannel(), listener);
  }

  public void disconnect() {
    logger.log("Performing disconnect.");
    sendMessage(Utils.createDisconnectMessage(clientId, accountToken));
    try {
      webSocket.close(FayeConstants.Codes.NORMAL_CLOSE, "Goodbye, Gitter!");
      webSocket = null;
      webSocketListener = null;
    } catch (IOException e) {
      onFailHappened(e);
    }
  }

  private void onFailHappened(Exception exception) {
    failListener.onFailed(exception);
  }

  private void performConnect() {
    logger.log("Performing connect.");
    sendMessage(Utils.createConnectionChannel(this.clientId, this.accountToken));
    connectionListener.onConnected();
    connectionListener = null;
  }

  private void performHandshake() {
    logger.log("Performing handshake.");
    this.handshakeListener = new HandshakeListener() {
      @Override
      public void onFailed(Exception ex) {
        logger.log("Handshake failed. Error: " + ex.getMessage());
        onFailHappened(ex);
      }

      @Override
      public void onHandshakeFinished() {
        logger.log("Successful handshake.");
        performConnect();
      }
    };

    Request request = new Request.Builder().url(GITTER_FAYE_ENDPOINT).build();
    WebSocketCall.create(new OkHttpClient(), request).enqueue(this.webSocketListener);
  }

  private void sendMessage(JsonObject message) {
    logger.log("Sending message: " + message);
    try {
      webSocket.sendMessage(WebSocket.PayloadType.TEXT, Utils.writeString(message.toString()));
    } catch (IOException e) {
      onFailHappened(e);
    }
  }

  private WebSocketListener webSocketListener = new WebSocketListener() {
    @Override
    public void onOpen(WebSocket socket, Response response) {
      webSocket = socket;
      sendMessage(Utils.createHandShakeJson(accountToken));

      pingTask = new TimerTask() {
        @Override
        public void run() {
          try {
            if (webSocket == null) {
//              Websocket is closed so we need to cancel ping task.
              cancel();
            } else {
              webSocket.sendPing(new Buffer());
            }
          } catch (IOException e) {
            onFailHappened(e);
          }
        }
      };

      new Timer().scheduleAtFixedRate(pingTask, DEFAULT_PING_INTERVAL_SEC * 1000,
          DEFAULT_PING_INTERVAL_SEC * 1000);
    }

    @Override
    public void onFailure(IOException e, Response response) {
      pingTask.cancel();
      failListener.onFailed(e);
    }

    @Override
    public void onMessage(BufferedSource payload, WebSocket.PayloadType type) throws IOException {
      Buffer buffer = new Buffer();
      payload.readAll(buffer);
      payload.close();

      JsonObject json = new Gson().fromJson(Utils.fromBuffer(buffer), JsonArray.class).get(0).getAsJsonObject();

      logger.log("Received message: " + json);

      String channel = json.get("channel").getAsString();
      if (HANDSHAKE.equals(channel)) {
        if (json.get(SUCCESS).getAsBoolean()) {
          clientId = json.get(CLIENT_ID).getAsString();
          handshakeListener.onHandshakeFinished();
        } else {
          handshakeListener.onFailed(new Exception(json.toString()));
        }
        return;
      } else if (SUBSCRIBE.equals(channel)) {
        String subscriptionChannel = json.get(SUBSCRIPTION).getAsString();
        MessageListener listener = channelListeners.get(subscriptionChannel);
        if (listener != null) {
          if (json.get(SUCCESS).getAsBoolean()) {
            listener.onSubscribed(channel);
          } else {
            listener.onFailed(channel, new Exception(json.toString()));
          }
        }

        return;
      } else if (UNSUBSCRIBE.equals(channel)) {
        String subscriptionChannel = json.get(SUBSCRIPTION).getAsString();
        MessageListener listener = channelListeners.get(subscriptionChannel);
        if (listener != null) {
          channelListeners.remove(subscriptionChannel);
          if (json.get(SUCCESS).getAsBoolean()) {
            listener.onUnSubscribed(subscriptionChannel);
          } else {
            listener.onFailed(channel, new Exception(json.toString()));
          }
        }

        return;
      } else if (DISCONNECT.equals(channel)) {
        logger.log("Disconnected from server.");
        return;
      } else {
        MessageListener listener = channelListeners.get(channel);
        if (listener != null) {
          listener.onMessage(channel, json.getAsJsonObject(DATA));
          return;
        }
      }

      logger.log("Listener for '" + channel + "' not found. Message: " + json);
    }

    @Override
    public void onPong(Buffer payload) {
    }

    @Override
    public void onClose(int code, String reason) {
      logger.log("Websocket closed. Code : " + code + ", Reason: " + reason + ".");
      pingTask.cancel();
    }
  };
}
