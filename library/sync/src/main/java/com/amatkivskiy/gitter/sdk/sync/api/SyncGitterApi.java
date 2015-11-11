package com.amatkivskiy.gitter.sdk.sync.api;

import com.amatkivskiy.gitter.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.sdk.model.response.*;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.SearchRoomsResponse;
import retrofit.http.*;

import java.util.List;
import java.util.Map;

public interface SyncGitterApi {
  @GET("/user")
  UserResponse getCurrentUser();

  @GET("/user/{userId}/rooms")
  RoomResponse getUserRooms(@Path("userId") String userId);

  @GET("/rooms")
  List<RoomResponse> getCurrentUserRooms();

  @GET("/user/{userId}/orgs")
  List<OrgResponse> getUserOrgs(@Path("userId") String userId);

  @GET("/user/{userId}/repos")
  List<RepoResponse> getUserRepos(@Path("userId") String userId);

  @GET("/user/{userId}/channels")
  List<RoomResponse> getUserChannels(@Path("userId") String userId);

  @GET("/rooms/{roomId}/users")
  List<UserResponse> getRoomUsers(@Path("roomId") String roomId);

  @GET("/rooms/{roomId}/channels")
  List<RoomResponse> getRoomChannels(@Path("roomId") String roomId);

  @POST("/rooms")
  @FormUrlEncoded
  RoomResponse joinRoom(@Field("uri") String roomUri);

  @GET("/rooms")
  SearchRoomsResponse searchRooms(@Query("q") String searchTerm);

  @GET("/user")
  SearchUsersResponse searchUsers(@Query("type") UserAccountType type,  @Query("q") String searchTerm);

  @GET("/user")
  SearchUsersResponse searchUsers(@Query("q") String searchTerm);

  @GET("/rooms")
  SearchRoomsResponse searchRooms(@Query("q") String searchTerm, @Query("limit") int limit);

  @DELETE("/rooms/{roomId}/users/{userId}")
  BooleanResponse leaveRoom(@Path("roomId") String roomId, @Path("userId") String userId);

  @GET("/user/me/suggestedRooms")
  List<RoomResponse> getSuggestedRooms();

  @POST("/user/{userId}/rooms/{roomId}/unreadItems")
  BooleanResponse markReadMessages(
      @Path("userId") String userId,
      @Path("roomId") String roomId,
      @Body UnreadRequestParam param);

  @GET("/user/{userId}/rooms/{roomId}/unreadItems")
  UnReadMessagesResponse getUnReadMessages(@Path("userId") String userId,  @Path("roomId") String roomId);

  @GET("/rooms/{roomId}/chatMessages")
  List<MessageResponse> getRoomMessages(@Path("roomId") String roomId, @QueryMap Map<String, String> options);

  @GET("/rooms/{roomId}/chatMessages/{messageId}")
  MessageResponse getRoomMessageById(@Path("roomId") String roomId, @Path("messageId") String messageId);

  @FormUrlEncoded
  @POST("/rooms/{roomId}/chatMessages")
  MessageResponse sendMessage(@Path("roomId") String roomId, @Field("text") String text);

  @FormUrlEncoded
  @PUT("/rooms/{roomId}/chatMessages/{chatMessageId}")
  MessageResponse updateMessage(@Path("roomId") String roomId,
                     @Path("chatMessageId") String chatMessageId,
                     @Field("text") String text);
}
