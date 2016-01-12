package com.amatkivskiy.gitter.sdk.async.faye.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.amatkivskiy.gitter.sdk.async.faye.client.AsyncGitterFayeClient;

import java.nio.charset.Charset;

import okio.Buffer;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.CONNECT;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.DISCONNECT;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.HANDSHAKE;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.SUBSCRIBE;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.UNSUBSCRIBE;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.ACCOUNT_TOKEN;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.CHANNEL;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.CLIENT_ID;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.CONNECTION_TYPE;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.EXT;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.MIN_VERSION;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.SUBSCRIPTION;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.SUPPORTED_CONNECTION_TYPES;
import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.JsonKeys.VERSION;

public class Utils {

  public static JsonObject createDisconnectMessage(String clientId, String accountToken) {
    JsonObject json = new JsonObject();
    json.addProperty(CHANNEL, DISCONNECT);
    json.addProperty(CLIENT_ID, clientId);
    json.addProperty(CONNECTION_TYPE, AsyncGitterFayeClient.VALUE_CONNECTION_TYPE);

    json.add(EXT, createAccountTokenObject(accountToken));

    return json;
  }

  public static JsonObject createConnectionChannel(String clientId, String accountToken) {
    JsonObject json = new JsonObject();
    json.addProperty(CHANNEL, CONNECT);
    json.addProperty(CLIENT_ID, clientId);
    json.addProperty(CONNECTION_TYPE, AsyncGitterFayeClient.VALUE_CONNECTION_TYPE);

    json.add(EXT, createAccountTokenObject(accountToken));

    return json;
  }

  public static JsonObject createChannelUnSubscription(String accountToken, String channel, String clientId) {
    JsonObject json = new JsonObject();
    json.addProperty(CHANNEL, UNSUBSCRIBE);
    json.addProperty(CLIENT_ID, clientId);
    json.addProperty(SUBSCRIPTION, channel);

    json.add(EXT, createAccountTokenObject(accountToken));

    return json;
  }

  public static JsonObject createChannelSubscription(String accountToken, String channel, String clientId) {
    JsonObject json = new JsonObject();
    json.addProperty(CHANNEL, SUBSCRIBE);
    json.addProperty(CLIENT_ID, clientId);
    json.addProperty(SUBSCRIPTION, channel);

    json.add(EXT, createAccountTokenObject(accountToken));

    return json;
  }

  public static JsonObject createHandShakeJson(String accountToken) {
    JsonObject json = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(new JsonPrimitive(AsyncGitterFayeClient.VALUE_CONNECTION_TYPE));
    json.add(SUPPORTED_CONNECTION_TYPES, jsonArray);
    json.addProperty(CHANNEL, HANDSHAKE);
    json.addProperty(VERSION, AsyncGitterFayeClient.VALUE_VERSION);
    json.addProperty(MIN_VERSION, AsyncGitterFayeClient.VALUE_MIN_VERSION);

    json.add(EXT, createAccountTokenObject(accountToken));

    return json;
  }

  public static JsonObject createAccountTokenObject(String accountToken) {
    JsonObject token = new JsonObject();
    token.addProperty(ACCOUNT_TOKEN, accountToken);

    return token;
  }

  public static Buffer writeString(String source) {
    return new Buffer().writeString(source, Charset.defaultCharset());
  }

  public static String fromBuffer(Buffer source) {
    return source.readString(Charset.defaultCharset());
  }
}
