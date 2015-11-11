package com.amatkivskiy.gitter.sdk.sync.client;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.GitterApiBuilder;
import com.amatkivskiy.gitter.sdk.converter.UserJsonDeserializer;
import com.amatkivskiy.gitter.sdk.model.request.ChatMessagesRequestParams;
import com.amatkivskiy.gitter.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.sdk.model.response.*;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.SearchRoomsResponse;
import com.amatkivskiy.gitter.sdk.sync.api.SyncGitterApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.converter.GsonConverter;
import retrofit.http.Path;

import java.util.HashMap;
import java.util.List;

public class SyncGitterApiClient {
  private SyncGitterApi api;

  public SyncGitterApiClient(SyncGitterApi api) {
    this.api = api;
  }

  public MessageResponse sendMessage(String roomId, String text) {
    return api.sendMessage(roomId, text);
  }

  public UserResponse getCurrentUser() {
    return api.getCurrentUser();
  }

  public RoomResponse getUserRooms(String userId) {
    return api.getUserRooms(userId);
  }

  public List<OrgResponse> getUserOrgs(String userId) {
    return api.getUserOrgs(userId);
  }

  public List<RepoResponse> getUserRepos(String userId) {
    return api.getUserRepos(userId);
  }

  public List<RoomResponse> getUserChannels(String userId) {
    return api.getUserChannels(userId);
  }

  public List<UserResponse> getRoomUsers(String roomId) {
    return api.getRoomUsers(roomId);
  }

  public List<RoomResponse> getRoomChannels(String roomId) {
    return api.getRoomChannels(roomId);
  }

  public RoomResponse joinRoom(String roomUri) {
    return api.joinRoom(roomUri);
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

  private HashMap<String, String> convertChatMessagesParamsToMap(ChatMessagesRequestParams params) {
    HashMap<String, String> options = new HashMap<>();
    if (params != null) {
      if (params.limit != null) {
        options.put(Constants.GitterRequestParams.LIMIT_PARAM, String.valueOf(params.limit.intValue()));
      }

      if (params.afterId != null) {
        options.put(Constants.GitterRequestParams.AFTER_ID_PARAM, params.afterId);
      }

      if (params.beforeId != null) {
        options.put(Constants.GitterRequestParams.BEFORE_ID_PARAM, params.beforeId);
      }

      if (params.skipCount != null) {
        options.put(Constants.GitterRequestParams.SKIP_PARAM, String.valueOf(params.skipCount.intValue()));
      }
    }

    return options;
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
