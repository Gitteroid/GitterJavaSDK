package com.amatkivskiy.gitter.rx.sdk.model.response.room;

import java.util.List;

public class SearchRoomsResponse {
  public final List<RoomResponse> results;

  public SearchRoomsResponse(List<RoomResponse> results) {
    this.results = results;
  }
}
