package com.amatkivskiy.gitter.sdk.rx.api;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Streaming;
import rx.Observable;

public interface RxGitterStreamingApi {

  @Streaming
  @GET("/rooms/{roomId}/chatMessages")
  Observable<Response> getRoomMessagesStream(@Path("roomId") String roomId);

  @Streaming
  @GET("/rooms/{roomId}/events")
  Observable<Response> getRoomEventsStream(@Path("roomId") String roomId);
}
