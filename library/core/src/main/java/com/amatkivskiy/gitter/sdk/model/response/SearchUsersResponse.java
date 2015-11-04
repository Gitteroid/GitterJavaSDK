package com.amatkivskiy.gitter.sdk.model.response;

import java.util.List;

public class SearchUsersResponse {
  public final int limit;
  public final int  skip;
  public final List<UserResponse> results;

  public SearchUsersResponse(int limit, int skip, List<UserResponse> results) {
    this.limit = limit;
    this.skip = skip;
    this.results = results;
  }
}
