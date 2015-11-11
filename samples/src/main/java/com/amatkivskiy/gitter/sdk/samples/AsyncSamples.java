package com.amatkivskiy.gitter.sdk.samples;

import com.amatkivskiy.gitter.sdk.GitterOauthUtils;
import com.amatkivskiy.gitter.sdk.credentials.GitterDeveloperCredentials;
import com.amatkivskiy.gitter.sdk.credentials.SimpleGitterCredentialsProvider;
import com.amatkivskiy.gitter.sdk.model.request.ChatMessagesRequestParams;
import com.amatkivskiy.gitter.sdk.model.request.UserAccountType;
import com.amatkivskiy.gitter.sdk.model.response.AccessTokenResponse;
import com.amatkivskiy.gitter.sdk.model.response.BooleanResponse;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.async.client.AsyncGitterApiClient;
import com.amatkivskiy.gitter.sdk.async.client.AsyncGitterAuthenticationClient;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class AsyncSamples {
  //  This information you can take from your Gitter dev account:
//  https://developer.gitter.im/apps
  private static final String OAUTH_KEY = "your_oauth_key";
  private static final String OAUTH_SECRET = "your_oauth_secret";
  private static final String REDIRECT_URL = "your_redirect_url";

  public static void main(String[] args) {
//    You need to setup GitterDeveloperCredentials with data from your Gitter Dev account.
//    This is required only for authentication.
    GitterDeveloperCredentials.init(new SimpleGitterCredentialsProvider(OAUTH_KEY, OAUTH_SECRET, REDIRECT_URL));

    getAccessTokenSample();
    getUserSample();
    getRoomChatMessages();
    leaveRoomSample();
    searchRoomsSample();
    searchUsersSample();
    markMessagesRead();
    getSuggestedRooms();
  }

  private static void getAccessTokenSample() {
    String gitterAccessUrl = GitterOauthUtils.buildOauthUrl();
    System.out.println("gitterAccessUrl = " + gitterAccessUrl);

//    Open browser with gitterAccessUrl and listen to redirects. When you catch redirect
//    extract code url parameter from url.
//    Example URL: some.redirect.url?code=deadbeef
    String code = "deadbeef";

//    Then you need to exchange code for access token.
    AsyncGitterAuthenticationClient authenticationClient = new AsyncGitterAuthenticationClient.Builder()
        .withLogLevel(RestAdapter.LogLevel.FULL)
        .build();

    authenticationClient.getAccessToken(code,
        new Callback<AccessTokenResponse>() {
          @Override
          public void success(AccessTokenResponse tokenResponse, Response response) {
//    From here yot can access api with your access token.
            System.out.println("Access token = " + tokenResponse.accessToken);

//    For example:
            new AsyncGitterApiClient.Builder()
                .withAccountToken(tokenResponse.accessToken)
                .build()
                .getCurrentUserRooms(new EmptyCallback<List<RoomResponse>>());
          }

          @Override
          public void failure(RetrofitError error) {
          }
        });
  }

  private static void getUserSample() {
    AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    client.getCurrentUser(new SuccessCallback<UserResponse>() {
      @Override
      public void success(UserResponse user, Response response) {
        System.out.println("user.displayName = " + user.displayName);
      }
    });
  }

  private static void getRoomChatMessages() {
    AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    ChatMessagesRequestParams params = new ChatMessagesRequestParams.ChatMessagesRequestParamsBuilder().limit(20).build();
    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.

    client.getRoomMessages(roomId, params, new SuccessCallback<List<MessageResponse>>() {
      @Override
      public void success(List<MessageResponse> messages, Response response) {
        System.out.println("Received " + messages.size() + " messages");
      }
    });
  }

  private static void leaveRoomSample() {
    AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.
    String userId = "user_id";

    client.leaveRoom(roomId, userId, new SuccessCallback<BooleanResponse>() {
      @Override
      public void success(BooleanResponse response, Response response2) {
        System.out.println("leaveRoomResponse = " + response.success);
      }
    });
  }

  private static void searchRoomsSample() {
    AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    client.searchRooms("gitter", 50, new SuccessCallback<List<RoomResponse>>() {
      @Override
      public void success(List<RoomResponse> roomResponses, Response response) {
        System.out.println("roomResponses.size() = " + roomResponses.size());
      }
    });
  }

  private static void searchUsersSample() {
    AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    client.searchUsers(UserAccountType.Gitter, "mr.robot", new SuccessCallback<List<UserResponse>>() {
      @Override
      public void success(List<UserResponse> userResponses, Response response) {
        System.out.println("userResponses = " + userResponses);
      }
    });
  }

  private static void markMessagesRead() {
    AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    ArrayList<String> ids = new ArrayList<>();
    ids.add("message_id1");
    ids.add("message_id2");
    ids.add("message_id3");

    String roomId = "room_id";
    String userId = "user_id";

    client.markReadMessages(userId, roomId, ids, new SuccessCallback<BooleanResponse>() {
      @Override
      public void success(BooleanResponse response, Response response2) {
        System.out.println("booleanResponse = " + response.success);
      }
    });
  }

  private static void getSuggestedRooms() {
    AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    client.getSuggestedRooms(new SuccessCallback<List<RoomResponse>>() {
      @Override
      public void success(List<RoomResponse> roomResponses, Response response) {
        System.out.println("rooms size = " + roomResponses.size());
      }
    });
  }
}
