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
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterAuthenticationClient;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterStreamingApiClient;
import com.amatkivskiy.gitter.sdk.rx.streaming.model.RoomEvent;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.functions.Action1;

public class RxSamples {
  //  This information you can take from your Gitter dev account:
//  https://developer.gitter.im/apps
  private static final String OAUTH_KEY = "your_oauth_key";
  private static final String OAUTH_SECRET = "your_oauth_secret";
  private static final String REDIRECT_URL = "your_redirect_url";

  public static void main(String[] args) {
//    You need to setup GitterDeveloperCredentials with data from your Gitter Dev account.
//    This is required only for authentication.
    GitterDeveloperCredentials.init(new SimpleGitterCredentialsProvider(OAUTH_KEY, OAUTH_SECRET, REDIRECT_URL));

//    getAccessTokenSample();
//    getUserSample();
//    getRoomChatMessages();
//    roomMessagesStreamSample();
//    leaveRoomSample();
//    searchRoomsSample();
//    searchUsersSample();
//    markMessagesRead();
//    getSuggestedRooms();
//    roomEventsStreamSample();
    testMethod();
  }

  private static void testMethod() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("c2fc54dc5bc835317b7b29f389383611d8940987")
        .build();

    BooleanResponse result = client.deleteRooom("57edcee740f3a6eec068229e").toBlocking().first();
    System.out.println(String.format("success : %s", result.success));

    System.exit(0);
  }

  private static void roomEventsStreamSample() {
    OkHttpClient okClient = new OkHttpClient();

    // Configure OkHttpClient not to drop connection rapidly.
    okClient.setReadTimeout(10, TimeUnit.MINUTES);

    RxGitterStreamingApiClient client = new RxGitterStreamingApiClient.Builder()
        .withAccountToken("user_access_token")
        .withClient(new OkClient(okClient))
        .build();

    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.

    client.getRoomEventsStream(roomId).subscribe(new Action1<RoomEvent>() {
      @Override
      public void call(RoomEvent event) {
        System.out.println(event.sent);
        System.out.println(event.meta);
      }
    });
  }

  private static void getSuggestedRooms() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    client.getSuggestedRooms().subscribe(new Action1<List<RoomResponse>>() {
      @Override
      public void call(List<RoomResponse> roomResponses) {
        System.out.println("rooms size = " + roomResponses.size());
      }
    });
  }

  private static void markMessagesRead() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    ArrayList<String> ids = new ArrayList<>();
    ids.add("message_id1");
    ids.add("message_id2");
    ids.add("message_id3");

    String roomId = "room_id";
    String userId = "user_id";

    client.markReadMessages(userId, roomId, ids).subscribe(new Action1<BooleanResponse>() {
      @Override
      public void call(BooleanResponse booleanResponse) {
        System.out.println("booleanResponse = " + booleanResponse.success);
      }
    });
  }

  private static void searchUsersSample() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    client.searchUsers(UserAccountType.Gitter, "mr.robot").subscribe(new Action1<List<UserResponse>>() {
      @Override
      public void call(List<UserResponse> userResponses) {
        System.out.println("userResponses = " + userResponses);
      }
    });
  }

  private static void searchRoomsSample() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    client.searchRooms("gitter", 50).subscribe(new Action1<List<RoomResponse>>() {
      @Override
      public void call(List<RoomResponse> roomResponses) {
        System.out.println("roomResponses.size() = " + roomResponses.size());
      }
    });
  }

  private static void leaveRoomSample() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.
    String userId = "user_id";

    client.leaveRoom(roomId, userId).subscribe(new Action1<BooleanResponse>() {
      @Override
      public void call(BooleanResponse response) {
        System.out.println("leaveRoomResponse = " + response.success);
      }
    });
  }

  private static void roomMessagesStreamSample() {
    OkHttpClient okClient = new OkHttpClient();

    // Configure OkHttpClient not to drop connection rapidly.
    okClient.setReadTimeout(10, TimeUnit.MINUTES);

    RxGitterStreamingApiClient client = new RxGitterStreamingApiClient.Builder()
        .withAccountToken("user_access_token")
        .withClient(new OkClient(okClient))
        .build();

    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.

    client.getRoomMessagesStream(roomId).subscribe(new Action1<MessageResponse>() {
      @Override
      public void call(MessageResponse messageResponse) {
        System.out.println("messageResponse = " + messageResponse);
      }
    });
  }

  private static void getUserSample() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    UserResponse user = client.getCurrentUser().toBlocking().first();
    System.out.println("user.displayName = " + user.displayName);
  }

  private static void getRoomChatMessages() {
    RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

    ChatMessagesRequestParams params = new ChatMessagesRequestParams.ChatMessagesRequestParamsBuilder().limit(20).build();
    String roomId = "533aa1485e986b0712f00ba5"; // gitterHQ/developers for example.

    List<MessageResponse> messages = client.getRoomMessages(roomId, params).toBlocking().first();
    System.out.println("Received " + messages.size() + " messages");
  }

  private static void getAccessTokenSample() {
    String gitterAccessUrl = GitterOauthUtils.buildOauthUrl();
    System.out.println("gitterAccessUrl = " + gitterAccessUrl);

//    Open browser with gitterAccessUrl and listen to redirects. When you catch redirect
//    extract code url parameter from url.
//    Example URL: some.redirect.url?code=deadbeef
    String code = "deadbeef";

//    Then you need to exchange code for access token.
    RxGitterAuthenticationClient authenticationClient = new RxGitterAuthenticationClient.Builder()
        .withLogLevel(RestAdapter.LogLevel.FULL)
        .build();
    AccessTokenResponse tokenResponse = authenticationClient.getAccessToken(code)
        .toBlocking()
        .first();

//    From here yot can access api with your access token.
    System.out.println("Access token = " + tokenResponse.accessToken);

//    For example:
    Observable<List<RoomResponse>> rooms = new RxGitterApiClient.Builder()
        .withAccountToken(tokenResponse.accessToken)
        .build()
        .getCurrentUserRooms();
  }
}
