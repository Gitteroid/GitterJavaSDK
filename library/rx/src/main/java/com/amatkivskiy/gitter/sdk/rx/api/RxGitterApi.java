package com.amatkivskiy.gitter.sdk.rx.api;

import com.amatkivskiy.gitter.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.sdk.model.request.UpdateRoomRequestParam;
import com.amatkivskiy.gitter.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;
import com.amatkivskiy.gitter.sdk.model.response.OrgResponse;
import com.amatkivskiy.gitter.sdk.model.response.RepoResponse;
import com.amatkivskiy.gitter.sdk.model.response.SearchUsersResponse;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.ban.BanResponse;
import com.amatkivskiy.gitter.sdk.model.response.group.GroupResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.SearchRoomsResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.welcome.WelcomeMessageContainer;
import com.amatkivskiy.gitter.sdk.model.response.room.welcome.WelcomeResponse;

import java.util.List;
import java.util.Map;

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
import rx.Observable;

public interface RxGitterApi {
  // User API
  @GET("/user")
  Observable<UserResponse> getCurrentUser();

  @GET("/user/{userId}/orgs")
  Observable<List<OrgResponse>> getUserOrgs(@Path("userId") String userId);

  @GET("/user/{userId}/repos")
  Observable<List<RepoResponse>> getUserRepos(@Path("userId") String userId);

  @GET("/user")
  Observable<SearchUsersResponse> searchUsers(@Query("type") UserAccountType type, @Query("q") String searchTerm);

  @GET("/user")
  Observable<SearchUsersResponse> searchUsers(@Query("q") String searchTerm);

  // Rooms API
  @POST("/rooms")
  @FormUrlEncoded
  Observable<RoomResponse> getRoomIdByUri(@Field("uri") String uri);

  @GET("/rooms/{roomId}")
  Observable<RoomResponse> getRoomById(@Path("roomId") String roomId);

  @GET("/user/{userId}/rooms")
  Observable<List<RoomResponse>> getUserRooms(@Path("userId") String userId);

  @GET("/rooms")
  Observable<List<RoomResponse>> getCurrentUserRooms();

  @GET("/rooms/{roomId}/users")
  Observable<List<UserResponse>> getRoomUsers(@Path("roomId") String roomId);

  @POST("/user/{userId}/rooms")
  @FormUrlEncoded
  Observable<RoomResponse> joinRoom(@Path("userId") String userId, @Field("id") String roomId);

  @PUT("/rooms/{roomId}")
  Observable<RoomResponse> updateRoom(@Path("roomId") String roomId, @Body UpdateRoomRequestParam param);

  @GET("/rooms")
  Observable<SearchRoomsResponse> searchRooms(@Query("q") String searchTerm);

  @GET("/rooms")
  Observable<SearchRoomsResponse> searchRooms(@Query("q") String searchTerm, @Query("limit") int limit);

  @DELETE("/rooms/{roomId}/users/{userId}")
  Observable<BooleanResponse> leaveRoom(@Path("roomId") String roomId, @Path("userId") String userId);

  @GET("/user/me/suggestedRooms")
  Observable<List<RoomResponse>> getSuggestedRooms();

  @DELETE("/rooms/{roomId}")
  Observable<BooleanResponse> deleteRoom(@Path("roomId") String roomId);

  // Messages API
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

  // Groups API
  @GET("/groups")
  Observable<List<GroupResponse>> getCurrentUserGroups(@Query("type") String type);

  @GET("/groups/{groupId}")
  Observable<GroupResponse> getGroupById(@Path("groupId") String groupId);

  @GET("/groups/{groupId}/rooms")
  Observable<List<RoomResponse>> getGroupRooms(@Path("groupId") String groupId);

  // Ban API
  @GET("/rooms/{roomId}/bans")
  Observable<List<BanResponse>> getBannedUsers(@Path("roomId") String roomId);

  @FormUrlEncoded
  @POST("/rooms/{roomId}/bans")
  Observable<BanResponse> banUser(@Path("roomId") String roomId, @Field("username") String username);

  @DELETE("/rooms/{roomId}/bans/{username}")
  Observable<BooleanResponse> unBanUser(@Path("roomId") String roomId, @Path("username") String username);

  // Welcome API
  @GET("/rooms/{roomId}/meta/welcome-message")
  Observable<WelcomeResponse> getRoomWelcome(@Path("roomId") String roomId);

  @FormUrlEncoded
  @PUT("/rooms/{roomId}/meta/welcome-message")
  Observable<WelcomeMessageContainer> setRoomWelcome(@Path("roomId") String roomId,
                                                     @Field("welcomeMessage") String message);
}
