package com.amatkivskiy.gitter.sdk;

public class Constants {
  public static class GitterEndpoints {
    public final static String GITTER_AUTHENTICATION_ENDPOINT = "https://gitter.im";
    public final static String GITTER_API_ENDPOINT = "https://api.gitter.im";
    public final static String GITTER_STREAMING_API_ENDPOINT = "https://stream.gitter.im";
    public final static String GITTER_API_ENDPOINT_VERSION = "v1";
  }

  public static class GitterRequestHeaderParams {
    public final static String AUTHORIZATION_REQUEST_HEADER = "Authorization";
    public final static String BEARER_REQUEST_HEADER = "Bearer";
  }

  public static class GitterRequestParams {
    public final static String SKIP_PARAM = "skip";
    public final static String BEFORE_ID_PARAM = "beforeId";
    public final static String AFTER_ID_PARAM = "afterId";
    public final static String LIMIT_PARAM = "limit";
  }

  public static class GitterOauth {
    public final static String OAUTH_URL = "https://gitter.im/login/oauth/authorize";
    public final static String OAUTH_URL_PARAMS_FORMAT = "%s?client_id=%s&response_type=%s&redirect_uri=%s";
    public final static String OAUTH_CODE_PARAMETER = "code";
    public final static String OAUTH_GRANT_TYPE_PARAMETER = "authorization_code";
  }
}