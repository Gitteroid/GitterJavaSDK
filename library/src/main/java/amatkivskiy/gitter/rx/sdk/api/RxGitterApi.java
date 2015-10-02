package amatkivskiy.gitter.rx.sdk.api;

import amatkivskiy.gitter.rx.sdk.model.request.UnreadRequestParam;
import amatkivskiy.gitter.rx.sdk.model.response.OrgResponse;
import amatkivskiy.gitter.rx.sdk.model.response.RepoResponse;
import amatkivskiy.gitter.rx.sdk.model.response.UserResponse;
import amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import amatkivskiy.gitter.rx.sdk.model.response.room.RoomResponse;
import retrofit.http.*;
import rx.Observable;

import java.util.List;
import java.util.Map;

public interface RxGitterApi {
  @GET("/user")
  Observable<UserResponse> getCurrentUser();

  @GET("/user/{userId}/rooms")
  Observable<List<RoomResponse>> getUserRooms(@Path("userId") String userId);

  @GET("/rooms")
  Observable<List<RoomResponse>> getCurrentUserRooms();

  @GET("/user/{userId}/orgs")
  Observable<List<OrgResponse>> getUserOrgs(@Path("userId") String userId);

  @GET("/user/{userId}/repos")
  Observable<List<RepoResponse>> getUserRepos(@Path("userId") String userId);

  @GET("/user/{userId}/channels")
  Observable<List<RoomResponse>> getUserChannels(@Path("userId") String userId);

  @POST("/rooms")
  @FormUrlEncoded
  Observable<RoomResponse> joinRoom(@Field("uri") String roomUri);

  @POST("/user/{userId}/rooms/{roomId}/unreadItems")
  Observable<String> markReadMessages(@Path("userId") String userId, @Path("roomId") String roomId,
                                      @Body UnreadRequestParam param);

  @GET("/rooms/{roomId}/chatMessages")
  Observable<List<MessageResponse>> getRoomMessages(
      @Path("roomId") String roomId,
      @QueryMap Map<String, String> options
  );

  @FormUrlEncoded
  @POST("/rooms/{roomId}/chatMessages")
  Observable<MessageResponse> sendMessage(
      @Path("roomId") String roomId, @Field("text") String text);

  @FormUrlEncoded
  @PUT("/rooms/{roomId}/chatMessages/{chatMessageId}")
  Observable<MessageResponse> updateMessage(@Path("roomId") String roomId,
                                            @Path("chatMessageId") String chatMessageId,
                                            @Field("text") String text);
}
