# GitterRxJavaSDK
Gitter.im Java SDK that facilitates communication with Gitter API. Uses RxJava and Retrofit.

## Description
This SDK consists of two parts:
- *Authentication API*
- *Rest API implementation*

**Authentication**
Please read [Authentication](https://developer.gitter.im/docs/authentication) on **Gitter Developer** article before.

## How to authenticate user with SDK

1) Setup **GitterDeveloperCredentials** with info from [here](https://developer.gitter.im/apps):
```
GitterDeveloperCredentials.init(new SimpleGitterCredentialsProvider(your_oauth_key, your_oauth_secret, your_redirect_url));
```
2) Get Gitter request access URL:
```
String gitterAccessUrl = GitterOauthUtils.buildOauthUrl();
```
3) Open this url in something like embedded browser and listen for redirects.
4) When user grants access gitter redirects back with url like:
```
http://some.redirect.url?code=deadbeef
```
extract ```code``` parameter value.
5)  Exchange code for access token:
```
RxGitterAuthenticationClient authenticationClient = new RxGitterAuthenticationClient.Builder().build();

authenticationClient.getAccessToken(code).subscribe(new Action1<AccessTokenResponse>() {
      @Override
      public void call(AccessTokenResponse accessTokenResponse) {
        System.out.println("Access token = " + accessTokenResponse.accessToken);
      }
    });
```
6) Save and use this ```accessToken``` to make requests to the REST API.

## How to get data from Gitter REST API
1)  Create ```RxGitterApiClient``` with help of ```RxGitterApiClient.Builder```:
```
RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();
```
also you can provide some Retrofit config for requests:
```
RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .withClient(new OkClient())
        .withExecutors(httpExecutor, callbackExecutor)
        .withLogLevel(RestAdapter.LogLevel.BASIC)
        .build();
```
2) Execute any request that you need:
```
client.getCurrentUser().subscribe(new Action1<UserResponse>() {
      @Override
      public void call(UserResponse user) {
        System.out.println("user.displayName = " + user.displayName);
      }
    });
```
or
```
ChatMessagesRequestParams params = new ChatMessagesRequestParamsBuilder().limit(20).build();
String roomId = "room_id";

client.getRoomMessages(roomId, params).subscribe(new Action1<List<MessageResponse>>() {
      @Override
      public void call(List<MessageResponse> messages) {
        System.out.println("Received " + messages.size() + " messages");
      }
    });
```
or
```
client.getUserChannels("user_id").subscribe(new Action1<List<RoomResponse>>() {
      @Override
      public void call(List<RoomResponse> rooms) {
        System.out.println("Received " + rooms.size() + " rooms");
      }
    });
```

Thats all =).

## Samples

You can see some code samples [here](https://github.com/Gitteroid/GitterRxJavaSDK/blob/master/samples/src/main/java/amatkivskiy/gitter/rx/sdk/samples/Samples.java)

### Feel free to ask any questions.
