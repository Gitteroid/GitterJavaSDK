package com.amatkivskiy.gitter.sdk.model.request;

public class ChatMessagesRequestParams {
  public final Integer skipCount;
  public final String beforeId;
  public final String afterId;
  public final Integer limit;

  private ChatMessagesRequestParams(Integer skipCount, String beforeId, String afterId, Integer limit) {
    this.skipCount = skipCount;
    this.beforeId = beforeId;
    this.afterId = afterId;
    this.limit = limit;
  }

  public static class ChatMessagesRequestParamsBuilder {
    private Integer skipCount;
    private String beforeId;
    private String afterId;
    private Integer limit;

    public ChatMessagesRequestParamsBuilder skip(int skipCount) {
      this.skipCount = skipCount;
      return this;
    }

    public ChatMessagesRequestParamsBuilder beforeId(String beforeId) {
      this.beforeId = beforeId;
      return this;
    }

    public ChatMessagesRequestParamsBuilder afterId(String afterId) {
      this.afterId = afterId;
      return this;
    }

    public ChatMessagesRequestParamsBuilder limit(int limit) {
      this.limit = limit;
      return this;
    }

    public ChatMessagesRequestParams build() {
      return new ChatMessagesRequestParams(skipCount, beforeId, afterId, limit);
    }
  }
}
