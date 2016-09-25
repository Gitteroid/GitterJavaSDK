package com.amatkivskiy.gitter.sdk.async.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.GitterApiBuilder;
import com.amatkivskiy.gitter.sdk.async.api.AsyncGitterApi;
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

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import static com.amatkivskiy.gitter.sdk.util.RequestUtils.convertChatMessagesParamsToMap;

public class AsyncGitterApiClient {
  private AsyncGitterApi api;

  public AsyncGitterApiClient(AsyncGitterApi api) {
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

  public void getRoomUsers(String roomId, String query, int skip, int limit, Callback<List<UserResponse>> callback) {
    api.getRoomUsers(roomId, query, skip, limit, callback);
  }

  public void getRoomChannels(String roomId, Callback<List<RoomResponse>> callback) {
    api.getRoomChannels(roomId, callback);
  }

  public void joinRoom(String roomUri, Callback<RoomResponse> callback) {
    api.joinRoom(roomUri, callback);
  }

  public void updateRoom(String roomId, UpdateRoomRequestParam params,
                                             Callback<RoomResponse> callback) {
    api.updateRoom(roomId, params, callback);
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

  public void getRoomMessageById(String roomId, String messageId, Callback<MessageResponse> callback) {
    api.getRoomMessageById(roomId, messageId, callback);
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

  public static class Builder extends GitterApiBuilder<Builder, AsyncGitterApiClient> {

    protected String getFullEndpointUrl() {
      return Constants.GitterEndpoints.GITTER_API_ENDPOINT + "/" + apiVersion + "/";
    }

    @Override
    public AsyncGitterApiClient build() {
      prepareDefaultBuilderConfig();

      Gson gson = new GsonBuilder()
          .registerTypeAdapter(UserResponse.class, new UserJsonDeserializer())
          .create();
      restAdapterBuilder.setConverter(new GsonConverter(gson));

      AsyncGitterApi api = restAdapterBuilder.build().create(AsyncGitterApi.class);
      return new AsyncGitterApiClient(api);
    }
  }
}
