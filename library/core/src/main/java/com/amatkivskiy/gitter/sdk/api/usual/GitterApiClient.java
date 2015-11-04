package com.amatkivskiy.gitter.sdk.api.usual;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.GitterApiBuilder;
import com.amatkivskiy.gitter.sdk.api.services.usual.GitterApi;
import com.amatkivskiy.gitter.sdk.converter.UserJsonDeserializer;
import com.amatkivskiy.gitter.sdk.model.request.ChatMessagesRequestParams;
import com.amatkivskiy.gitter.sdk.model.request.UnreadRequestParam;
import com.amatkivskiy.gitter.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.sdk.model.response.*;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.UnReadMessagesResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.SearchRoomsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import java.util.HashMap;
import java.util.List;

public class GitterApiClient {
  private GitterApi api;

  public GitterApiClient(GitterApi api) {
    this.api = api;
  }

  public void sendMessage(String roomId, String text, Callback<MessageResponse> callback) {
    api.sendMessage(roomId, text, callback);
  }

  public void getCurrentUser(Callback<UserResponse> callback) {
    api.getCurrentUser(callback);
  }

  public void getUserRooms(String userId, Callback<List<RoomResponse>> callback) {
    api.getUserRooms(userId, callback);
  }

  public void getUserOrgs(String userId, Callback<List<OrgResponse>> callback) {
    api.getUserOrgs(userId, callback);
  }

  public void getUserRepos(String userId, Callback<List<RepoResponse>> callback) {
    api.getUserRepos(userId, callback);
  }

  public void getUserChannels(String userId, Callback<List<RoomResponse>> callback) {
    api.getUserChannels(userId, callback);
  }

  public void getRoomUsers(String roomId, Callback<List<UserResponse>> callback) {
    api.getRoomUsers(roomId, callback);
  }

  public void getRoomChannels(String roomId, Callback<List<RoomResponse>> callback) {
    api.getRoomChannels(roomId, callback);
  }

  public void joinRoom(String roomUri, Callback<RoomResponse> callback) {
    api.joinRoom(roomUri, callback);
  }

  /**
   * Removes specified user from the room. It can be used to leave room.
   *
   * @param roomId Id of the room.
   * @param userId Id of the user to remove.
   * @return true if successful.
   */
  public void leaveRoom(String roomId, String userId, Callback<BooleanResponse> callback) {
    api.leaveRoom(roomId, userId, callback);
  }

  public void searchRooms(String searchTerm, final Callback<List<RoomResponse>> callback) {
    api.searchRooms(searchTerm, new Callback<SearchRoomsResponse>() {
      @Override
      public void success(SearchRoomsResponse searchRoomsResponse, Response response) {
        callback.success(searchRoomsResponse.results, response);
      }

      @Override
      public void failure(RetrofitError error) {
        callback.failure(error);
      }
    });
  }

  /**
   * @return suggested rooms for the current user.
   */
  public void getSuggestedRooms(Callback<List<RoomResponse>> callback) {
    api.getSuggestedRooms(callback);
  }

  public void searchRooms(String searchTerm, int limit, final Callback<List<RoomResponse>> callback) {
    api.searchRooms(searchTerm, limit, new Callback<SearchRoomsResponse>() {
      @Override
      public void success(SearchRoomsResponse searchRoomsResponse, Response response) {
        callback.success(searchRoomsResponse.results, response);
      }

      @Override
      public void failure(RetrofitError error) {
        callback.failure(error);
      }
    });
  }

  public void searchUsers(UserAccountType type, String searchTerm, final Callback<List<UserResponse>> callback) {
    api.searchUsers(type, searchTerm, new Callback<SearchUsersResponse>() {
      @Override
      public void success(SearchUsersResponse searchUsersResponse, Response response) {
        callback.success(searchUsersResponse.results, response);
      }

      @Override
      public void failure(RetrofitError error) {
        callback.failure(error);
      }
    });
  }

  public void searchUsers(String searchTerm, final Callback<List<UserResponse>> callback) {
    api.searchUsers(searchTerm, new Callback<SearchUsersResponse>() {
      @Override
      public void success(SearchUsersResponse searchUsersResponse, Response response) {
        callback.success(searchUsersResponse.results, response);
      }

      @Override
      public void failure(RetrofitError error) {
        callback.failure(error);
      }
    });
  }

  public void getCurrentUserRooms(Callback<List<RoomResponse>> callback) {
    api.getCurrentUserRooms(callback);
  }

  public void getRoomMessages(String roomId, ChatMessagesRequestParams params, Callback<List<MessageResponse>> callback) {
    api.getRoomMessages(roomId, convertChatMessagesParamsToMap(params), callback);
  }

  public void getRoomMessages(String roomId, Callback<List<MessageResponse>> callback) {
    getRoomMessages(roomId, null, callback);
  }

  public void updateMessage(String roomId, String chatMessageId, String text, Callback<MessageResponse> callback) {
    api.updateMessage(roomId, chatMessageId, text, callback);
  }

  public void markReadMessages(String userId, String roomId, List<String> chatIds, Callback<BooleanResponse> callback) {
    api.markReadMessages(userId, roomId, new UnreadRequestParam(chatIds), callback);
  }

  public void getUnReadMessages(String userId, String roomId, Callback<UnReadMessagesResponse> callback) {
    api.getUnReadMessages(userId, roomId, callback);
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

  public static class Builder extends GitterApiBuilder<Builder, GitterApiClient> {

    protected String getFullEndpointUrl() {
      return Constants.GitterEndpoints.GITTER_API_ENDPOINT + "/" + apiVersion + "/";
    }

    @Override
    public GitterApiClient build() {
      prepareDefaultBuilderConfig();

      Gson gson = new GsonBuilder()
          .registerTypeAdapter(UserResponse.class, new UserJsonDeserializer())
          .create();
      restAdapterBuilder.setConverter(new GsonConverter(gson));

      GitterApi api = restAdapterBuilder.build().create(GitterApi.class);
      return new GitterApiClient(api);
    }
  }
}
