package com.amatkivskiy.gitter.sdk.async.faye.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.amatkivskiy.gitter.sdk.async.faye.FayeConstants;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.ChannelListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.ConnectionListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.DisconnectionListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.FailListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.HandshakeListener;
import com.amatkivskiy.gitter.sdk.async.faye.interfaces.Logger;
import com.amatkivskiy.gitter.sdk.async.faye.listeners.AbstractChannelListener;
import com.amatkivskiy.gitter.sdk.async.faye.util.Utils;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.ApiEndpoint.GITTER_FAYE_ENDPOINT;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.DISCONNECT;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.HANDSHAKE;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.SUBSCRIBE;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.UNSUBSCRIBE;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.CLIENT_ID;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.DATA;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.SUBSCRIPTION;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.SUCCESS;

public class AsyncGitterFayeClient {
  public static final String VALUE_VERSION = "1.0";
  public static final String VALUE_MIN_VERSION = "1.0beta";
  public static final String VALUE_CONNECTION_TYPE = "websocket";

  public static final int DEFAULT_PING_INTERVAL_SEC = 5;

  // Keep connection timer task
  private Logger logger = new Logger() {
    @Override
    public void log(String message) {

    }
  };

  private String accountToken;
  private String clientId;
  private WebSocket webSocket;
  private OkHttpClient okHttpClient;
  private HashMap<String, ChannelListener> channelListeners = new HashMap<>();

  private boolean catchDisconnection = true;

  // State listeners
  private HandshakeListener handshakeListener;
  private ConnectionListener connectionListener;
  private DisconnectionListener disconnectionListener = new DisconnectionListener() {
    @Override
    public void onDisconnected() {

    }
  };
  private FailListener failListener = new FailListener() {
    @Override
    public void onFailed(Throwable throwable) {
      logger.log("Caught error: " + throwable);
    }
  };

  public AsyncGitterFayeClient(String accountToken,
                               DisconnectionListener onDisconnected,
                               FailListener failListener,
                               OkHttpClient okHttpClient) {
    this.accountToken = accountToken;
    if (onDisconnected != null) this.disconnectionListener = onDisconnected;
    if (failListener != null) this.failListener = failListener;
    if (okHttpClient != null) this.okHttpClient = okHttpClient;
  }

  /**
   * Performs connection to the Faye server.
   *
   * @param onConnected callback for connection request.
   */
  public void connect(ConnectionListener onConnected) {
    this.catchDisconnection = true;
    channelListeners = new HashMap<>();
    connectionListener = onConnected;
    performHandshake();
  }

  /**
   * Enables logging for all main operation that this client performs.
   *
   * @param logger custom logger.
   */
  public void setLogger(Logger logger) {
    if (logger == null) {
      throw new IllegalArgumentException("Logger should not be null.");
    }

    this.logger = logger;
  }

  public void subscribe(String channel, ChannelListener listener) {
    channelListeners.put(channel, listener);
    sendMessage(Utils.createChannelSubscription(this.accountToken, channel, this.clientId));
  }

  public void subscribe(AbstractChannelListener listener) {
    subscribe(listener.getChannel(), listener);
  }

  public void unSubscribe(String channel) {
    sendMessage(Utils.createChannelUnSubscription(this.accountToken, channel, this.clientId));
  }

  public void unSubscribe(AbstractChannelListener listener) {
    sendMessage(Utils.createChannelUnSubscription(this.accountToken, listener.getChannel(),
        this.clientId));
  }

  /**
   * Disconnects this client from Faye server.
   */
  public void disconnect() {
    logger.log("Performing disconnect.");
    this.catchDisconnection = false;
    sendMessage(Utils.createDisconnectMessage(clientId, accountToken));
    closeWebSocket();
  }

  private void closeWebSocket() {
    webSocket.close(FayeConstants.Codes.NORMAL_CLOSE, "Goodbye, Gitter!");
  }

  private void onFailHappened(Throwable throwable) {
    failListener.onFailed(throwable);
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
      public void onFailed(Throwable throwable) {
        logger.log("Handshake failed. Error: " + throwable.getMessage());

        closeWebSocket();

        onFailHappened(throwable);
      }

      @Override
      public void onHandshakeFinished() {
        logger.log("Successful handshake.");
        performConnect();
      }
    };

    Request request = new Request.Builder().url(GITTER_FAYE_ENDPOINT).build();
    this.okHttpClient.newWebSocket(request, createWebSocketListener());
  }

  private void sendMessage(JsonObject message) {
    logger.log("Sending message: " + message);
    webSocket.send(message.toString());
  }

  private WebSocketListener createWebSocketListener() {
    return new WebSocketListener() {

      @Override
      public void onOpen(WebSocket socket, Response response) {
        webSocket = socket;
        sendMessage(Utils.createHandShakeJson(accountToken));
      }

      @Override
      public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        failListener.onFailed(t);
      }

      @Override
      public void onMessage(WebSocket webSocket, String text) {
        JsonObject json = new Gson().fromJson(text, JsonArray.class).get(0).getAsJsonObject();

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
          ChannelListener listener = channelListeners.get(subscriptionChannel);
          if (listener != null) {
            if (json.get(SUCCESS).getAsBoolean()) {
              listener.onSubscribed(channel, Utils.parseSnapshotJson(json));
            } else {
              listener.onFailed(channel, new Exception(json.toString()));
            }
          }

          return;
        } else if (UNSUBSCRIBE.equals(channel)) {
          String subscriptionChannel = json.get(SUBSCRIPTION).getAsString();
          ChannelListener listener = channelListeners.get(subscriptionChannel);
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
          ChannelListener listener = channelListeners.get(channel);
          if (listener != null) {
            listener.onMessage(channel, json.getAsJsonObject(DATA));
            return;
          }
        }

        logger.log("Listener for '" + channel + "' not found. Message: " + json);
      }

      @Override
      public void onClosing(WebSocket webSocket, int code, String reason) {
        logger.log("Websocket closing. Code : " + code + ", Reason: " + reason + ".");
        cleanUp();

        if (catchDisconnection) disconnectionListener.onDisconnected();
      }
    };
  }

  private void cleanUp() {
    webSocket = null;

    channelListeners.clear();
    channelListeners = null;
  }
}
