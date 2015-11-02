package com.amatkivskiy.gitter.rx.sdk.api.services.rx;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Streaming;
import rx.Observable;

public interface RxGitterStreamingApi {

  @Streaming
  @GET("/rooms/{roomId}/chatMessages")
  Observable<Response> getRoomStream(@Path("roomId") String roomId);
}
