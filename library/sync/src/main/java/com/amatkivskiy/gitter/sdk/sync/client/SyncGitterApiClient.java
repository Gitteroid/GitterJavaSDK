package com.amatkivskiy.gitter.sdk.sync.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.GitterApiBuilder;
import com.amatkivskiy.gitter.sdk.converter.UserJsonDeserializer;
import com.amatkivskiy.gitter.sdk.model.request.ChatMessagesRequestParams;
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
import com.amatkivskiy.gitter.sdk.sync.api.SyncGitterApi;

import java.util.List;

import retrofit.converter.GsonConverter;

import static com.amatkivskiy.gitter.sdk.util.RequestUtils.convertChatMessagesParamsToMap;

public class SyncGitterApiClient {
  private SyncGitterApi api;

  public SyncGitterApiClient(SyncGitterApi api) {
    this.api = api;
  }

  // User API
  public UserResponse getCurrentUser() {
    return api.getCurrentUser();
  }

  public List<OrgResponse> getUserOrgs(String userId) {
    return api.getUserOrgs(userId);
  }

  public List<RepoResponse> getUserRepos(String userId) {
    return api.getUserRepos(userId);
  }

  public List<UserResponse> searchUsers(UserAccountType type, String searchTerm) {
    SearchUsersResponse response = api.searchUsers(type, searchTerm);
    return response.results;
  }

  public List<UserResponse> searchUsers(String searchTerm) {
    SearchUsersResponse response = api.searchUsers(searchTerm);
    return response.results;
  }

  public List<RoomResponse> getCurrentUserRooms() {
    return api.getCurrentUserRooms();
  }

  // Rooms API
  public RoomResponse getUserRooms(String userId) {
    return api.getUserRooms(userId);
  }

  public List<UserResponse> getRoomUsers(String roomId) {
    return api.getRoomUsers(roomId);
  }

  public RoomResponse joinRoom(String userId, String roomId) {
    return api.joinRoom(userId, roomId);
  }

  public RoomResponse updateRoom(String roomId, UpdateRoomRequestParam params) {
    return api.updateRoom(roomId, params);
  }

  /**
   * Removes specified user from the room. It can be used to leave room.
   *
   * @param roomId Id of the room.
   * @param userId Id of the user to remove.
   * @return true if successful.
   */
  public BooleanResponse leaveRoom(String roomId, String userId) {
    return api.leaveRoom(roomId, userId);
  }

  public List<RoomResponse> searchRooms(String searchTerm) {
    SearchRoomsResponse response = api.searchRooms(searchTerm);
    return response.results;
  }

  /**
   * @return suggested rooms for the current user.
   */
  public List<RoomResponse> getSuggestedRooms() {
    return api.getSuggestedRooms();
  }

  public List<RoomResponse> searchRooms(String searchTerm, int limit) {
    SearchRoomsResponse response = api.searchRooms(searchTerm, limit);
    return response.results;
  }

  public BooleanResponse deleteRoom(String roomId) {
    return api.deleteRoom(roomId);
  }

  // Messages API
  public MessageResponse sendMessage(String roomId, String text) {
    return api.sendMessage(roomId, text);
  }

  public List<MessageResponse> getRoomMessages(String roomId, ChatMessagesRequestParams params) {
    return api.getRoomMessages(roomId, convertChatMessagesParamsToMap(params));
  }

  public List<MessageResponse> getRoomMessages(String roomId) {
    return getRoomMessages(roomId, null);
  }

  public MessageResponse getRoomMessageById(String roomId, String messageId) {
    return api.getRoomMessageById(roomId, messageId);
  }

  public MessageResponse updateMessage(String roomId, String chatMessageId, String text) {
    return api.updateMessage(roomId, chatMessageId, text);
  }

  public BooleanResponse markReadMessages(String userId, String roomId, List<String> chatIds) {
    return api.markReadMessages(userId, roomId, new UnreadRequestParam(chatIds));
  }

  public UnReadMessagesResponse getUnReadMessages(String userId, String roomId) {
    return api.getUnReadMessages(userId, roomId);
  }

  // Groups API
  public List<GroupResponse> getCurrentUserGroups() {
    return api.getCurrentUserGroups(null);
  }

  public List<GroupResponse> getCurrentUserAdminGroups() {
    return api.getCurrentUserGroups("admin");
  }

  public GroupResponse getGroupById(String groupId) {
    return api.getGroupById(groupId);
  }

  public List<RoomResponse> getGroupRooms(String groupId) {
    return api.getGroupRooms(groupId);
  }

  // Ban API
  public List<BanResponse> getBannedUsers(String roomId) {
    return api.getBannedUsers(roomId);
  }

  /**
   * Ban user of the specific room. Be careful when banning user in the private room:
   * BanResponse.user and BanResponse.bannedBy will be null.
   *
   * @param roomId   id of the room.
   * @param username name of the user.
   */
  public BanResponse banUser(String roomId, String username) {
    return api.banUser(roomId, username);
  }

  public static class Builder extends GitterApiBuilder<Builder, SyncGitterApiClient> {

    protected String getFullEndpointUrl() {
      return Constants.GitterEndpoints.GITTER_API_ENDPOINT + "/" + apiVersion + "/";
    }

    @Override
    public SyncGitterApiClient build() {
      prepareDefaultBuilderConfig();

      Gson gson = new GsonBuilder()
          .registerTypeAdapter(UserResponse.class, new UserJsonDeserializer())
          .create();
      restAdapterBuilder.setConverter(new GsonConverter(gson));

      SyncGitterApi api = restAdapterBuilder.build().create(SyncGitterApi.class);
      return new SyncGitterApiClient(api);
    }
  }
}
