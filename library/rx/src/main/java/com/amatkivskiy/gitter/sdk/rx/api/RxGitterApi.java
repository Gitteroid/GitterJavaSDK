package com.amatkivskiy.gitter.sdk.rx.api;

import com.amatkivskiy.gitter.sdk.model.response.*;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.SearchRoomsResponse;
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


  @GET("/rooms/{roomId}/users")
  Observable<List<UserResponse>> getRoomUsers(@Path("roomId") String roomId);

  @GET("/rooms/{roomId}/channels")
  Observable<List<RoomResponse>> getRoomChannels(@Path("roomId") String roomId);

  @POST("/rooms")
  @FormUrlEncoded
  Observable<RoomResponse> joinRoom(@Field("uri") String roomUri);

  @GET("/rooms")
  Observable<SearchRoomsResponse> searchRooms(@Query("q") String searchTerm);

  @GET("/user")
  Observable<SearchUsersResponse> searchUsers(@Query("type") UserAccountType type, @Query("q") String searchTerm);

  @GET("/user")
  Observable<SearchUsersResponse> searchUsers(@Query("q") String searchTerm);

  @GET("/rooms")
  Observable<SearchRoomsResponse> searchRooms(@Query("q") String searchTerm, @Query("limit") int limit);

  @DELETE("/rooms/{roomId}/users/{userId}")
  Observable<BooleanResponse> leaveRoom(@Path("roomId") String roomId, @Path("userId") String userId);

  @GET("/user/me/suggestedRooms")
  Observable<List<RoomResponse>> getSuggestedRooms();

  @POST("/user/{userId}/rooms/{roomId}/unreadItems")
  Observable<BooleanResponse> markReadMessages(@Path("userId") String userId, @Path("roomId") String roomId,
                                      @Body UnreadRequestParam param);

  @GET("/user/{userId}/rooms/{roomId}/unreadItems")
  Observable<UnReadMessagesResponse> getUnReadMessages(@Path("userId") String userId, @Path("roomId") String roomId);

  @GET("/rooms/{roomId}/chatMessages")
  Observable<List<MessageResponse>> getRoomMessages(
      @Path("roomId") String roomId,
      @QueryMap Map<String, String> options
  );

  @GET("/rooms/{roomId}/chatMessages/{messageId}")
  Observable<MessageResponse> getRoomMessageById(
      @Path("roomId") String roomId,
      @Path("messageId") String messageId
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
