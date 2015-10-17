package com.amatkivskiy.gitter.rx.sdk.api;

import com.amatkivskiy.gitter.rx.sdk.Constants;
import com.amatkivskiy.gitter.rx.sdk.api.builder.GitterApiBuilder;
import com.amatkivskiy.gitter.rx.sdk.converter.UserJsonDeserializer;
import com.amatkivskiy.gitter.rx.sdk.model.request.ChatMessagesRequestParams;
import com.amatkivskiy.gitter.rx.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.rx.sdk.model.response.LeaveRoomResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.OrgResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.RepoResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.rx.sdk.model.response.room.SearchRoomsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Func1;

import java.util.HashMap;
import java.util.List;

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

  public Observable<List<RoomResponse>> getRoomChannels(String roomId) {
    return api.getRoomChannels(roomId);
  }

  public Observable<RoomResponse> joinRoom(String roomUri) {
    return api.joinRoom(roomUri);
  }

  public Observable<LeaveRoomResponse> leaveRoom(String roomId, String userId) {
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

  public Observable<List<RoomResponse>> getCurrentUserRooms() {
    return api.getCurrentUserRooms();
  }

  public Observable<List<MessageResponse>> getRoomMessages(String roomId, ChatMessagesRequestParams params) {
    return api.getRoomMessages(roomId, convertChatMessagesParamsToMap(params));
  }

  public Observable<List<MessageResponse>> getRoomMessages(String roomId) {
    return getRoomMessages(roomId, null);
  }

  public Observable<MessageResponse> updateMessage(String roomId, String chatMessageId, String text) {
    return api.updateMessage(roomId, chatMessageId, text);
  }

  public Observable<String> markReadMessages(String userId, String roomId, List<String> chatIds) {
    return api.markReadMessages(userId, roomId, new UnreadRequestParam(chatIds));
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
