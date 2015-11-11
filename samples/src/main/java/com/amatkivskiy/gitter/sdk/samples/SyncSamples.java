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
import com.amatkivskiy.gitter.sdk.sync.client.SyncGitterApiClient;
import com.amatkivskiy.gitter.sdk.sync.client.SyncGitterAuthenticationClient;
import retrofit.RestAdapter;

import java.util.ArrayList;
import java.util.List;

public class SyncSamples {
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
    SyncGitterAuthenticationClient authenticationClient = new SyncGitterAuthenticationClient.Builder()
        .withLogLevel(RestAdapter.LogLevel.FULL)
        .build();

    AccessTokenResponse tokenResponse = authenticationClient.getAccessToken(code);
//    From here yot can access api with your access token.
    System.out.println("Access token = " + tokenResponse.accessToken);

//    For example:
    new SyncGitterApiClient.Builder()
        .withAccountToken(tokenResponse.accessToken)
        .build()
        .getCurrentUserRooms();
  }

  private static void getUserSample() {
    SyncGitterApiClient client = new SyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    UserResponse user = client.getCurrentUser();
    System.out.println("user.displayName = " + user.displayName);
  }

  private static void getRoomChatMessages() {
    SyncGitterApiClient client = new SyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    ChatMessagesRequestParams params = new ChatMessagesRequestParams.ChatMessagesRequestParamsBuilder().limit(20).build();
    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.

    List<MessageResponse> messages = client.getRoomMessages(roomId, params);
    System.out.println("Received " + messages.size() + " messages");
  }

  private static void leaveRoomSample() {
    SyncGitterApiClient client = new SyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.
    String userId = "user_id";

    BooleanResponse response = client.leaveRoom(roomId, userId);
    System.out.println("leaveRoomResponse = " + response.success);
  }

  private static void searchRoomsSample() {
    SyncGitterApiClient client = new SyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    List<RoomResponse> roomResponses = client.searchRooms("gitter", 50);
    System.out.println("roomResponses.size() = " + roomResponses.size());
  }

  private static void searchUsersSample() {
    SyncGitterApiClient client = new SyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    List<UserResponse> userResponses = client.searchUsers(UserAccountType.Gitter, "mr.robot");
    System.out.println("userResponses = " + userResponses);
  }

  private static void markMessagesRead() {
    SyncGitterApiClient client = new SyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    ArrayList<String> ids = new ArrayList<>();
    ids.add("message_id1");
    ids.add("message_id2");
    ids.add("message_id3");

    String roomId = "room_id";
    String userId = "user_id";

    BooleanResponse response = client.markReadMessages(userId, roomId, ids);
    System.out.println("booleanResponse = " + response.success);
  }

  private static void getSuggestedRooms() {
    SyncGitterApiClient client = new SyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    List<RoomResponse> roomResponses = client.getSuggestedRooms();
    System.out.println("rooms size = " + roomResponses.size());
  }
}
