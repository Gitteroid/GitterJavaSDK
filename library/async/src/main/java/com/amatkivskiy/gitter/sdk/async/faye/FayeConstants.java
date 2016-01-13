package com.amatkivskiy.gitter.sdk.async.faye;

public class FayeConstants {
  public class ApiEndpoint {
    public static final String GITTER_FAYE_ENDPOINT = "https://ws.gitter.im/faye";
  }

  public class FayeChannels {
    public static final String HANDSHAKE = "/meta/handshake";
    public static final String CONNECT = "/meta/connect";
    public static final String DISCONNECT = "/meta/disconnect";
    public static final String SUBSCRIBE = "/meta/subscribe";
    public static final String UNSUBSCRIBE = "/meta/unsubscribe";
    public static final String ROOM_MESSAGES_CHANNEL_TEMPLATE = "/api/v1/rooms/%s/chatMessages";
    public static final String ROOM_USERS_PRESENCE_CHANNEL_TEMPLATE = "/api/v1/rooms/%s";
    public static final String ROOM_USERS_CHANNEL_TEMPLATE = "/api/v1/rooms/%s/users";
  }

  public class Codes {
    public static final int NORMAL_CLOSE = 1000;
  }

  public class JsonKeys {
    public static final String CHANNEL = "channel";
    public static final String SUCCESS = "successful";
    public static final String CLIENT_ID = "clientId";
    public static final String VERSION = "version";
    public static final String MIN_VERSION = "minimumVersion";
    public static final String SUBSCRIPTION = "subscription";
    public static final String SUPPORTED_CONNECTION_TYPES = "supportedConnectionTypes";
    public static final String CONNECTION_TYPE = "connectionType";
    public static final String DATA = "data";
    public static final String ID = "id";
    public static final String EXT = "ext";
    public static final String ERROR = "error";
    public static final String ACCOUNT_TOKEN = "token";
  }


}
