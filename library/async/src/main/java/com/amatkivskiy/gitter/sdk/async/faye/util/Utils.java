package com.amatkivskiy.gitter.sdk.async.faye.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import com.amatkivskiy.gitter.sdk.async.faye.client.AsyncGitterFayeClient;
import com.amatkivskiy.gitter.sdk.converter.UserJsonDeserializer;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

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
    JsonObject json = createBaseJson(accountToken, null, clientId, DISCONNECT);
    json.addProperty(CONNECTION_TYPE, AsyncGitterFayeClient.VALUE_CONNECTION_TYPE);

    return json;
  }

  public static JsonObject createConnectionChannel(String clientId, String accountToken) {
    JsonObject json = createBaseJson(accountToken, null, clientId, CONNECT);
    json.addProperty(CONNECTION_TYPE, AsyncGitterFayeClient.VALUE_CONNECTION_TYPE);

    return json;
  }

  public static JsonObject createChannelUnSubscription(String accountToken, String channel, String clientId) {
    return createBaseJson(accountToken, channel, clientId, UNSUBSCRIBE);
  }

  public static JsonObject createChannelSubscription(String accountToken, String channel, String clientId) {
    return createBaseJson(accountToken, channel, clientId, SUBSCRIBE);
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

  private static JsonObject createBaseJson(String accountToken, String subscription, String clientId, String channel) {
    JsonObject json = new JsonObject();
    json.addProperty(CHANNEL, channel);
    json.addProperty(CLIENT_ID, clientId);

    if (subscription != null) json.addProperty(SUBSCRIPTION, subscription);

    json.add(EXT, createAccountTokenObject(accountToken));

    return json;
  }

  private static JsonObject createAccountTokenObject(String accountToken) {
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

  public static List<MessageResponse> parseSnapshotJson(JsonObject parent) {
    if (!parent.has(EXT)) {
      return Collections.emptyList();
    }

    JsonArray snapshotJsonArray = parent.get(EXT)
        .getAsJsonObject()
        .getAsJsonArray("snapshot");

    Gson gson = new GsonBuilder()
        .registerTypeAdapter(UserResponse.class, new UserJsonDeserializer())
        .create();

    Type messagesListType = new TypeToken<List<MessageResponse>>() {}.getType();
    return gson.fromJson(snapshotJsonArray, messagesListType);
  }
}
