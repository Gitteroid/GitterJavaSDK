package com.amatkivskiy.gitter.sdk.model.request;

public class RoomUsersRequestParams {
  public final String searchQuery;
  public final Integer skipCount;
  public final Integer limit;

  private RoomUsersRequestParams(String searchQuery, Integer skipCount, Integer limit) {
    this.searchQuery = searchQuery;
    this.skipCount = skipCount;
    this.limit = limit;
  }

  public static class RoomUsersRequestParamsBuilder {
    private String searchQuery;
    private Integer skipCount;
    private Integer limit;

    public RoomUsersRequestParamsBuilder() {
      //Api default values
      this.searchQuery = "";
      this.skipCount = 0;
      this.limit = 30;
    }

    public RoomUsersRequestParamsBuilder skip(int skipCount) {
      this.skipCount = skipCount;
      return this;
    }

    public RoomUsersRequestParamsBuilder limit(int limit) {
      this.limit = limit;
      return this;
    }

    public RoomUsersRequestParamsBuilder searchQuery(String query) {
      this.searchQuery = query;
      return this;
    }

    public RoomUsersRequestParams build() {
      return new RoomUsersRequestParams(searchQuery, skipCount, limit);
    }
  }
}
