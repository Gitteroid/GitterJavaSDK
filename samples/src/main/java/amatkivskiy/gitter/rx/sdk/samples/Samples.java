package amatkivskiy.gitter.rx.sdk.samples;

import amatkivskiy.gitter.rx.sdk.GitterOauthUtils;
import amatkivskiy.gitter.rx.sdk.api.RxGitterApiClient;
import amatkivskiy.gitter.rx.sdk.api.RxGitterAuthenticationClient;
import amatkivskiy.gitter.rx.sdk.credentials.GitterDeveloperCredentials;
import amatkivskiy.gitter.rx.sdk.credentials.SimpleGitterCredentialsProvider;
import amatkivskiy.gitter.rx.sdk.model.request.ChatMessagesRequestParams;
import amatkivskiy.gitter.rx.sdk.model.request.ChatMessagesRequestParams.ChatMessagesRequestParamsBuilder;
import amatkivskiy.gitter.rx.sdk.model.response.AccessTokenResponse;
import amatkivskiy.gitter.rx.sdk.model.response.UserResponse;
import amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;

import java.util.List;

public class Samples {
//  This information you can take from your Gitter dev account:
//  https://developer.gitter.im/apps
  private static final String OAUTH_KEY = "your_oauth_key";
  private static final String OAUTH_SECRET= "your_oauth_secret";
  private static final String REDIRECT_URL = "your_redirect_url";

  public static void main(String[] args) {
//    You need to setup GitterDeveloperCredentials with data from your Gitter Dev account.
//    This is required only for authentication.
    GitterDeveloperCredentials.init(new SimpleGitterCredentialsProvider(OAUTH_KEY, OAUTH_SECRET, REDIRECT_URL));

    getAccessTokenSample();
    getUserSample();
    getRoomChatMessages();
  }

  private static void getUserSample() {
    RxGitterApiClient client = new RxGitterApiClient("user_access_token");
    UserResponse user = client.getCurrentUser().toBlocking().first();
    System.out.println("user.displayName = " + user.displayName);
  }

  private static void getRoomChatMessages() {
    RxGitterApiClient client = new RxGitterApiClient("user_access_token");

    ChatMessagesRequestParams params = new ChatMessagesRequestParamsBuilder().limit(20).build();
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
    AccessTokenResponse tokenResponse = new RxGitterAuthenticationClient().getAccessToken(code).toBlocking().first();
//    From here yot can access api with your access token.
    System.out.println("Access token = " + tokenResponse.accessToken);

//    For example:
    new RxGitterApiClient(tokenResponse.accessToken).getCurrentUserRooms();
  }
}
