package com.amatkivskiy.gitter.sdk.rx.client;

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
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.SearchRoomsResponse;
import com.amatkivskiy.gitter.sdk.rx.api.RxGitterApi;

import java.util.List;

import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Func1;

import static com.amatkivskiy.gitter.sdk.util.RequestUtils.convertChatMessagesParamsToMap;

public class RxGitterApiClient {
  private RxGitterApi api;

  public RxGitterApiClient(RxGitterApi api) {
    this.api = api;
  }

  public Observable<MessageResponse> sendMessage(String roomId, String text) {
    return api.sendMessage(roomId, text);
  }

  public Observable<UserResponse> getCurrentUser() {
    return api.getCurrentUser();
  }

  public Observable<List<RoomResponse>> getUserRooms(String userId) {
    return api.getUserRooms(userId);
  }

  public Observable<List<OrgResponse>> getUserOrgs(String userId) {
    return api.getUserOrgs(userId);
  }

  public Observable<List<RepoResponse>> getUserRepos(String userId) {
    return api.getUserRepos(userId);
  }

  public Observable<List<RoomResponse>> getUserChannels(String userId) {
    return api.getUserChannels(userId);
  }

  public Observable<List<UserResponse>> getRoomUsers(String roomId) {
    return api.getRoomUsers(roomId);
  }

  public Observable<List<UserResponse>> getRoomUsers(String roomId, String query, int skip, int limit) {
    return api.getRoomUsers(roomId, query, skip, limit);
  }

  public Observable<List<RoomResponse>> getRoomChannels(String roomId) {
    return api.getRoomChannels(roomId);
  }

  public Observable<RoomResponse> joinRoom(String roomUri) {
    return api.joinRoom(roomUri);
  }

  public Observable<RoomResponse> updateRoom(String roomId, UpdateRoomRequestParam params) {
    return api.updateRoom(roomId, params);
  }

  /**
   * Removes specified user from the room. It can be used to leave room.
   *
   * @param roomId Id of the room.
   * @param userId Id of the user to remove.
   * @return true if successful.
   */
  public Observable<BooleanResponse> leaveRoom(String roomId, String userId) {
    return api.leaveRoom(roomId, userId);
  }

  public Observable<List<RoomResponse>> searchRooms(String searchTerm) {
    return api.searchRooms(searchTerm).map(new Func1<SearchRoomsResponse, List<RoomResponse>>() {
      @Override
      public List<RoomResponse> call(SearchRoomsResponse searchRoomsResponse) {
        return searchRoomsResponse.results;
      }
    });
  }

  /**
   * @return suggested rooms for the current user.
   */
  public Observable<List<RoomResponse>> getSuggestedRooms() {
    return api.getSuggestedRooms();
  }

  public Observable<List<RoomResponse>> searchRooms(String searchTerm, int limit) {
    return api.searchRooms(searchTerm, limit).map(new Func1<SearchRoomsResponse, List<RoomResponse>>() {
      @Override
      public List<RoomResponse> call(SearchRoomsResponse searchRoomsResponse) {
        return searchRoomsResponse.results;
      }
    });
  }

  public Observable<List<UserResponse>> searchUsers(UserAccountType type, String searchTerm) {
    return api.searchUsers(type, searchTerm).map(new Func1<SearchUsersResponse, List<UserResponse>>() {
      @Override
      public List<UserResponse> call(SearchUsersResponse searchUsersResponse) {
        return searchUsersResponse.results;
      }
    });
  }

  public Observable<List<UserResponse>> searchUsers(String searchTerm) {
    return api.searchUsers(searchTerm).map(new Func1<SearchUsersResponse, List<UserResponse>>() {
      @Override
      public List<UserResponse> call(SearchUsersResponse searchUsersResponse) {
        return searchUsersResponse.results;
      }
    });
  }

  public Observable<List<RoomResponse>> getCurrentUserRooms() {
    return api.getCurrentUserRooms();
  }

  public Observable<List<MessageResponse>> getRoomMessages(String roomId, ChatMessagesRequestParams params) {
    return api.getRoomMessages(roomId, convertChatMessagesParamsToMap(params));
  }

  public Observable<MessageResponse> getRoomMessageById( String roomId, String messageId) {
    return api.getRoomMessageById(roomId, messageId);
  }

  public Observable<List<MessageResponse>> getRoomMessages(String roomId) {
    return getRoomMessages(roomId, null);
  }

  public Observable<MessageResponse> updateMessage(String roomId, String chatMessageId, String text) {
    return api.updateMessage(roomId, chatMessageId, text);
  }

  public Observable<BooleanResponse> markReadMessages(String userId, String roomId, List<String> chatIds) {
    return api.markReadMessages(userId, roomId, new UnreadRequestParam(chatIds));
  }

  public Observable<UnReadMessagesResponse> getUnReadMessages(String userId, String roomId) {
    return api.getUnReadMessages(userId, roomId);
  }

  public static class Builder extends GitterApiBuilder<Builder, RxGitterApiClient> {

    protected String getFullEndpointUrl() {
      return Constants.GitterEndpoints.GITTER_API_ENDPOINT + "/" + apiVersion + "/";
    }

    @Override
    public RxGitterApiClient build() {
      prepareDefaultBuilderConfig();

      Gson gson = new GsonBuilder()
          .registerTypeAdapter(UserResponse.class, new UserJsonDeserializer())
          .create();
      restAdapterBuilder.setConverter(new GsonConverter(gson));

      RxGitterApi api = restAdapterBuilder.build().create(RxGitterApi.class);
      return new RxGitterApiClient(api);
    }
  }
}
