package amatkivskiy.gitter.rx.sdk.api;

import amatkivskiy.gitter.rx.sdk.Constants;
import amatkivskiy.gitter.rx.sdk.converter.UserConverter;
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

public class RxGitterApiClient extends BaseApiClient {
  public RxGitterApiClient(final String token) {
    RequestInterceptor requestInterceptor = new RequestInterceptor() {
      @Override
      public void intercept(RequestFacade requestFacade) {
        requestFacade.addHeader(Constants.GitterRequestHeaderParams.AUTHORIZATION_REQUEST_HEADER, Constants.GitterRequestHeaderParams.BEARER_REQUEST_HEADER + " " + token);
      }
    };
    adapterBuilder.setRequestInterceptor(requestInterceptor);
  }

  public Observable<MessageResponse> sendMessage(String roomId, String text) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).sendMessage(roomId, text);
  }

  public Observable<UserResponse> getCurrentUser() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(UserResponse.class, new UserConverter())
        .create();
    adapterBuilder.setConverter(new GsonConverter(gson));

    return createApi(adapterBuilder.build(), RxGitterApi.class).getCurrentUser();
  }

  public Observable<List<RoomResponse>> getUserRooms(String userId) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).getUserRooms(userId);
  }

  public Observable<List<OrgResponse>> getUserOrgs(String userId) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).getUserOrgs(userId);
  }

  public Observable<List<RepoResponse>> getUserRepos(String userId) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).getUserRepos(userId);
  }

  public Observable<List<RoomResponse>> getUserChannels(String userId) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).getUserChannels(userId);
  }

  public Observable<RoomResponse> joinRoom(String roomUri) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).joinRoom(roomUri);
  }

  public Observable<List<RoomResponse>> getCurrentUserRooms() {
    return createApi(adapterBuilder.build(), RxGitterApi.class).getCurrentUserRooms();
  }

  public Observable<List<MessageResponse>> getRoomMessages(String roomId, ChatMessagesRequestParams params) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).getRoomMessages(roomId,
        convertChatMessagesParamsToMap(params));
  }

  public Observable<List<MessageResponse>> getRoomMessages(String roomId) {
    return getRoomMessages(roomId, null);
  }

  public Observable<MessageResponse> updateMessage(String roomId, String chatMessageId, String text) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).updateMessage(roomId, chatMessageId, text);
  }

  public Observable<String> markReadMessages(String userId, String roomId, List<String> chatIds) {
    return createApi(adapterBuilder.build(), RxGitterApi.class).markReadMessages(userId, roomId,
        new UnreadRequestParam(chatIds));
  }

  @Override
  protected String getEndpointUrl() {
    return Constants.GitterEndpoints.GITTER_API_ENDPOINT;
  }

  @Override
  protected String getEndpointVersion() {
    return "v1";
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
}
