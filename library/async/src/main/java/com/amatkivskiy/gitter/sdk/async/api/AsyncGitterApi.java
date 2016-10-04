package com.amatkivskiy.gitter.sdk.async.api;

import com.amatkivskiy.gitter.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.sdk.model.request.UpdateRoomRequestParam;
import com.amatkivskiy.gitter.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;
import com.amatkivskiy.gitter.sdk.model.response.OrgResponse;
import com.amatkivskiy.gitter.sdk.model.response.RepoResponse;
import com.amatkivskiy.gitter.sdk.model.response.SearchUsersResponse;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.group.GroupResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.SearchRoomsResponse;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface AsyncGitterApi {
  // User Api
  @GET("/user")
  void getCurrentUser(Callback<UserResponse> callback);

  @GET("/rooms")
  void getCurrentUserRooms(Callback<List<RoomResponse>> callback);

  @GET("/user/{userId}/orgs")
  void getUserOrgs(@Path("userId") String userId, Callback<List<OrgResponse>> callback);

  @GET("/user/{userId}/repos")
  void getUserRepos(@Path("userId") String userId, Callback<List<RepoResponse>> callback);

  @GET("/user")
  void searchUsers(
      @Query("type") UserAccountType type,
      @Query("q") String searchTerm,
      Callback<SearchUsersResponse> callback
  );

  @GET("/user")
  void searchUsers(@Query("q") String searchTerm, Callback<SearchUsersResponse> callback);

  // Rooms Api
  @GET("/rooms/{roomId}")
  void getRoomById(@Path("roomId") String roomId, Callback<RoomResponse> callback);

  @POST("/user/{userId}/rooms")
  @FormUrlEncoded
  void joinRoom(@Path("userId") String userId, @Field("id") String roomId,
                Callback<RoomResponse> callback);

  @GET("/rooms/{roomId}/users")
  void getRoomUsers(@Path("roomId") String roomId, Callback<List<UserResponse>> callback);

  @GET("/user/{userId}/rooms")
  void getUserRooms(@Path("userId") String userId, Callback<List<RoomResponse>> callback);

  @PUT("/rooms/{roomId}")
  void updateRoom(@Path("roomId") String roomId, @Body UpdateRoomRequestParam param,
                  Callback<RoomResponse> callback);

  @GET("/rooms")
  void searchRooms(@Query("q") String searchTerm, Callback<SearchRoomsResponse> callback);

  @GET("/rooms")
  void searchRooms(@Query("q") String searchTerm, @Query("limit") int limit, Callback<SearchRoomsResponse> callback);

  @DELETE("/rooms/{roomId}/users/{userId}")
  void leaveRoom(@Path("roomId") String roomId, @Path("userId") String userId, Callback<BooleanResponse> callback);

  @GET("/user/me/suggestedRooms")
  void getSuggestedRooms(Callback<List<RoomResponse>> callback);

  @DELETE("/rooms/{roomId}")
  void deleteRoom(@Path("roomId") String roomId, Callback<BooleanResponse> callback);

  // Messages API
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

  @GET("/rooms/{roomId}/chatMessages/{messageId}")
  void getRoomMessageById(
      @Path("roomId") String roomId,
      @Path("messageId") String messageId,
      Callback<MessageResponse> callback
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

  // Groups API
  @GET("/groups")
  void getCurrentUserGroups(@Query("type") String type, Callback<List<GroupResponse>> callback);

  @GET("/groups/{groupId}")
  void getGroupById(@Path("groupId") String groupId, Callback<GroupResponse> callback);

  @GET("/groups/{groupId}/rooms")
  void getGroupRooms(@Path("groupId") String groupId, Callback<List<RoomResponse>> callback);
}
