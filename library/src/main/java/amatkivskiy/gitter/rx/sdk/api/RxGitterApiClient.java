package amatkivskiy.gitter.rx.sdk.api;

import amatkivskiy.gitter.rx.sdk.Constants;
import amatkivskiy.gitter.rx.sdk.api.builder.BaseApiBuilder;
import amatkivskiy.gitter.rx.sdk.converter.UserJsonDeserializer;
import amatkivskiy.gitter.rx.sdk.model.request.ChatMessagesRequestParams;
import amatkivskiy.gitter.rx.sdk.model.request.UnreadRequestParam;
import amatkivskiy.gitter.rx.sdk.model.response.OrgResponse;
import amatkivskiy.gitter.rx.sdk.model.response.RepoResponse;
import amatkivskiy.gitter.rx.sdk.model.response.UserResponse;
import amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import amatkivskiy.gitter.rx.sdk.model.response.room.RoomResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RequestInterceptor;
import retrofit.converter.GsonConverter;
import rx.Observable;

import java.util.HashMap;
import java.util.List;

import static amatkivskiy.gitter.rx.sdk.Constants.GitterEndpoints.GITTER_API_ENDPOINT;
import static amatkivskiy.gitter.rx.sdk.Constants.GitterEndpoints.GITTER_API_ENDPOINT_VERSION;
import static amatkivskiy.gitter.rx.sdk.Constants.GitterRequestHeaderParams.AUTHORIZATION_REQUEST_HEADER;
import static amatkivskiy.gitter.rx.sdk.Constants.GitterRequestHeaderParams.BEARER_REQUEST_HEADER;

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

  public Observable<RoomResponse> joinRoom(String roomUri) {
    return api.joinRoom(roomUri);
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

  public static class Builder extends BaseApiBuilder<Builder, RxGitterApiClient> {
    private String accountToken;
    private String apiVersion = GITTER_API_ENDPOINT_VERSION;

    public Builder withAccountToken(String accountToken) {
      this.accountToken = accountToken;
      return this;
    }

    public Builder withApiVersion(String apiVersion) {
      this.apiVersion = apiVersion;
      return this;
    }

    private String getFullEndpointUrl() {
      return GITTER_API_ENDPOINT + "/" + apiVersion + "/";
    }

    @Override
    public RxGitterApiClient build() {
      Gson gson = new GsonBuilder()
          .registerTypeAdapter(UserResponse.class, new UserJsonDeserializer())
          .create();
      restAdapterBuilder.setConverter(new GsonConverter(gson));

      restAdapterBuilder.setEndpoint(getFullEndpointUrl());

      RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade requestFacade) {
          requestFacade.addHeader(AUTHORIZATION_REQUEST_HEADER, BEARER_REQUEST_HEADER + " " + accountToken);
        }
      };
      restAdapterBuilder.setRequestInterceptor(requestInterceptor);

      RxGitterApi api = restAdapterBuilder.build().create(RxGitterApi.class);
      return new RxGitterApiClient(api);
    }
  }
}
