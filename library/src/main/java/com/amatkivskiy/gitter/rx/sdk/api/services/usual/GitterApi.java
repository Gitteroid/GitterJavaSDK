package com.amatkivskiy.gitter.rx.sdk.api.services.usual;

import com.amatkivskiy.gitter.rx.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.rx.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.rx.sdk.model.response.*;
import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.room.SearchRoomsResponse;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;
import java.util.Map;

public interface GitterApi {
  @GET("/user")
  void getCurrentUser(Callback<UserResponse> callback);

  @GET("/user/{userId}/rooms")
  void getUserRooms(@Path("userId") String userId, Callback<List<RoomResponse>> callback);

  @GET("/rooms")
  void getCurrentUserRooms(Callback<List<RoomResponse>> callback);

  @GET("/user/{userId}/orgs")
  void getUserOrgs(@Path("userId") String userId, Callback<List<OrgResponse>> callback);

  @GET("/user/{userId}/repos")
  void getUserRepos(@Path("userId") String userId, Callback<List<RepoResponse>> callback);

  @GET("/user/{userId}/channels")
  void getUserChannels(@Path("userId") String userId, Callback<List<RoomResponse>> callback);


  @GET("/rooms/{roomId}/users")
  void getRoomUsers(@Path("roomId") String roomId, Callback<List<UserResponse>> callback);

  @GET("/rooms/{roomId}/channels")
  void getRoomChannels(@Path("roomId") String roomId, Callback<List<RoomResponse>> callback);

  @POST("/rooms")
  @FormUrlEncoded
  void joinRoom(@Field("uri") String roomUri, Callback<RoomResponse> callback);

  @GET("/rooms")
  void searchRooms(@Query("q") String searchTerm, Callback<SearchRoomsResponse> callback);

  @GET("/user")
  void searchUsers(
      @Query("type") UserAccountType type,
      @Query("q") String searchTerm,
      Callback<SearchUsersResponse> callback
  );

  @GET("/user")
  void searchUsers(@Query("q") String searchTerm, Callback<SearchUsersResponse> callback);

  @GET("/rooms")
  void searchRooms(@Query("q") String searchTerm, @Query("limit") int limit, Callback<SearchRoomsResponse> callback);

  @DELETE("/rooms/{roomId}/users/{userId}")
  void leaveRoom(@Path("roomId") String roomId, @Path("userId") String userId, Callback<BooleanResponse> callback);

  @GET("/user/me/suggestedRooms")
  void getSuggestedRooms(Callback<List<RoomResponse>> callback);

  @POST("/user/{userId}/rooms/{roomId}/unreadItems")
  void markReadMessages(
      @Path("userId") String userId,
      @Path("roomId") String roomId,
      @Body UnreadRequestParam param,
      Callback<BooleanResponse> callback);

  @GET("/user/{userId}/rooms/{roomId}/unreadItems")
  void getUnReadMessages(
      @Path("userId") String userId,
      @Path("roomId") String roomId,
      Callback<UnReadMessagesResponse> callback
  );

  @GET("/rooms/{roomId}/chatMessages")
  void getRoomMessages(
      @Path("roomId") String roomId,
      @QueryMap Map<String, String> options,
      Callback<List<MessageResponse>> callback
  );

  @FormUrlEncoded
  @POST("/rooms/{roomId}/chatMessages")
  void sendMessage(
      @Path("roomId") String roomId,
      @Field("text") String text,
      Callback<MessageResponse> callback);

  @FormUrlEncoded
  @PUT("/rooms/{roomId}/chatMessages/{chatMessageId}")
  void updateMessage(@Path("roomId") String roomId,
                     @Path("chatMessageId") String chatMessageId,
                     @Field("text") String text,
                     Callback<MessageResponse> callback);
}
