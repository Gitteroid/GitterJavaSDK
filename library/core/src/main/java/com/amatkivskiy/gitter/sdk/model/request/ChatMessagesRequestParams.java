package com.amatkivskiy.gitter.sdk.model.request;

public class ChatMessagesRequestParams extends BasicRequestParams {
  public final String beforeId;
  public final String afterId;
  public final String aroundId;

  private ChatMessagesRequestParams(Integer skipCount, String beforeId, String afterId,
                                    String aroundId, String searchQuery, Integer limit) {
    super(skipCount, searchQuery, limit);
    this.beforeId = beforeId;
    this.afterId = afterId;
    this.aroundId = aroundId;
  }

  public static class ChatMessagesRequestParamsBuilder {
    private Integer skipCount;
    private String beforeId;
    private String afterId;
    private String aroundId;
    private String searchQuery;
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

    public ChatMessagesRequestParamsBuilder aroundId(String id) {
      this.aroundId = id;
      return this;
    }

    public ChatMessagesRequestParamsBuilder searchQuery(String query) {
      this.searchQuery = query;
      return this;
    }

    public ChatMessagesRequestParams build() {
      return new ChatMessagesRequestParams(skipCount, beforeId, afterId, aroundId, searchQuery, limit);
    }
  }
}
