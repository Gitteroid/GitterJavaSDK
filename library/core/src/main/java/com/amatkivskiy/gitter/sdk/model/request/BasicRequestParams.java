package com.amatkivskiy.gitter.sdk.model.request;

public class BasicRequestParams {
  public final Integer skipCount;
  public final String searchQuery;
  public final Integer limit;

  public BasicRequestParams(Integer skipCount, String searchQuery, Integer limit) {
    this.skipCount = skipCount;
    this.searchQuery = searchQuery;
    this.limit = limit;
  }

  public static class BasicRequestParamsBuilder {
    private Integer skipCount;
    private String searchQuery;
    private Integer limit;

    public BasicRequestParamsBuilder skipCount(Integer skipCount) {
      this.skipCount = skipCount;
      return this;
    }

    public BasicRequestParamsBuilder searchQuery(String searchQuery) {
      this.searchQuery = searchQuery;
      return this;
    }

    public BasicRequestParamsBuilder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public BasicRequestParams build() {
      return new BasicRequestParams(skipCount, searchQuery, limit);
    }
  }
}
