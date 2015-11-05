# Gitter.im Java SDK

[ ![Download](https://api.bintray.com/packages/amatkivskiy/maven/gitter.rx.sdk/images/download.svg) ](https://bintray.com/amatkivskiy/maven/gitter.rx.sdk/_latestVersion)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GitterRxJavaSDK-green.svg?style=flat)](https://android-arsenal.com/details/1/2599)

[![Join the chat at https://gitter.im/Gitteroid/GitterRxJavaSDK](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Gitteroid/GitterRxJavaSDK?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Gitter.im Java SDK that facilitates communication with Gitter API.

It provides two approaches to work with API:
- RxJava approach;
- Async (callback) approach.

## Setup

Add gradle dependency:

For RxJava:
```groovy
repositories {
      jcenter()
}

dependencies {
      compile 'com.github.amatkivskiy:gitter.sdk.rx:1.3'
}
```

For async:
```groovy
repositories {
      jcenter()
}

dependencies {
      compile 'com.github.amatkivskiy:gitter.sdk.async:1.3'
}
```

## Release notes
- 1.3
	- Refactored library structure
	- Added async api support.
	- Added async api samples.
- 1.2.1
	- Added ability to retrieve unread messages.
- 1.2.0
	- Added ability to search users
	- Added ability to search rooms
	- Added ability to leave room
- 1.1.0
	- Added room messages streaming API.

## Features

- Authentication

*Rooms resource*
- List rooms
- Room users
- Channels
- Join a room
- Remove user from the room
- Leave room
- Search rooms

*User resource*
- Current user
- User rooms
- User orgs
- User repos
- User channels
- Search users

*Messages resource*
- Unread items
- List messages
- Send a message
- Update a message

*:heavy_exclamation_mark: Streaming (Avalible only in Rx part.*)
- Room messages stream

## Description
**Authentication**
Please read [Authentication](https://developer.gitter.im/docs/authentication) article on **Gitter Developer**  before.

## How to authenticate user with SDK

1) Setup **GitterDeveloperCredentials** with info from [here](https://developer.gitter.im/apps):
```java
GitterDeveloperCredentials.init(new SimpleGitterCredentialsProvider(your_oauth_key, your_oauth_secret, your_redirect_url));
```
2) Get Gitter request access URL:
```java
String gitterAccessUrl = GitterOauthUtils.buildOauthUrl();
```
3) Open this url in something like embedded browser and listen for redirects.
4) When user grants access gitter redirects back with url like:
```
http://some.redirect.url?code=deadbeef
```
extract ```code``` parameter value.

5)  Exchange code for access token:
 - Rx:
```java
RxGitterAuthenticationClient authenticationClient = new RxGitterAuthenticationClient.Builder().build();

authenticationClient.getAccessToken(code).subscribe(new Action1<AccessTokenResponse>() {
      @Override
      public void call(AccessTokenResponse accessTokenResponse) {
        System.out.println("Access token = " + accessTokenResponse.accessToken);
      }
    });
```

- Async:
```java
AsyncGitterAuthenticationClient authenticationClient = new AsyncGitterAuthenticationClient.Builder()
        .build();

authenticationClient.getAccessToken(code, new Callback<AccessTokenResponse>() {
	
		@Override
        public void success(AccessTokenResponse tokenResponse, Response response) {
		System.out.println("Access token = " + accessTokenResponse.accessToken);
        }

        @Override
        public void failure(RetrofitError error) {}
});
```
6) Save and use this ```accessToken``` to make requests to the REST API.

## How to get data from Gitter REST API
1)  Create ```GitterApiClient``` with help of ```GitterApiClient.Builder```:
- Rx:
```java
RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();
```
- Async:
```
AsyncGitterApiClient client = new AsyncGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();
```
also you can provide some Retrofit config for requests (same for Rx and Async):
```java
RxGitterApiClient client = new RxGitterApiClient.Builder()
        .withAccountToken("user_access_token")
        .withClient(new OkClient())
        .withExecutors(httpExecutor, callbackExecutor)
        .withLogLevel(RestAdapter.LogLevel.BASIC)
        .build();
```
2) Execute any request that you need:
- Rx:
```java
client.getCurrentUser().subscribe(new Action1<UserResponse>() {
      @Override
      public void call(UserResponse user) {
        System.out.println("user.displayName = " + user.displayName);
      }
    });
```
- Async:
```java
client.getCurrentUser(new Callback<UserResponse>() {
      @Override
      public void success(UserResponse userResponse, Response response) {
        System.out.println("userResponse.displayName = " + userResponse.displayName);
      }

      @Override
      public void failure(RetrofitError error) {
        System.err.println("error = " + error);
      }
    });
```
or
- Rx:
```java
ChatMessagesRequestParams params = new ChatMessagesRequestParamsBuilder().limit(20).build();
String roomId = "room_id";

client.getRoomMessages(roomId, params).subscribe(new Action1<List<MessageResponse>>() {
      @Override
      public void call(List<MessageResponse> messages) {
        System.out.println("Received " + messages.size() + " messages");
      }
    });
```
- Async:
```java
ChatMessagesRequestParams params = new ChatMessagesRequestParamsBuilder().limit(20).build();
String roomId = "room_id";

client.getRoomMessages(roomId, params, new Callback<List<MessageResponse>>() {
      @Override
      public void success(List<MessageResponse> messages, Response response) {
        System.out.println("Received " + messages.size() + " messages");
      }

      @Override
      public void failure(RetrofitError error) {}
    });
```
or
- Rx:
```java
client.getUserChannels("user_id").subscribe(new Action1<List<RoomResponse>>() {
      @Override
      public void call(List<RoomResponse> rooms) {
        System.out.println("Received " + rooms.size() + " rooms");
      }
    });
```
- Async:
```java
client.getUserChannels("user_id", new Callback<List<RoomResponse>>() {
      @Override
      public void success(List<RoomResponse> rooms, Response response) {
        System.out.println("Received " + rooms.size() + " rooms");
      }

      @Override
      public void failure(RetrofitError error) {}
    });
```
## How to get streaming data from Gitter Streaming API
### :heavy_exclamation_mark: Please don't set any log level for *RxGitterStreamingApiClient* as it blocks the stream.
:heavy_exclamation_mark: If you get `java.net.SocketTimeoutException: Read timed out` try to encrease `ReadTimeout` in your `retrofit.client.Client` and spicify this client for `GutterApiClient` (`withClient()`).

```
RxGitterStreamingApiClient client = new RxGitterStreamingApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

String roomId = "room_id";

client.getRoomMessagesStream(roomId).subscribe(new Action1<MessageResponse>() {
	@Override
	public void call(MessageResponse messageResponse) {
		System.out.println("messageResponse = " + messageResponse);
	}
});
```
Thats all =).

## Samples

You can see some code samples [here](https://github.com/Gitteroid/GitterRxJavaSDK/blob/master/samples/src/main/java/com/amatkivskiy/gitter/rx/sdk/samples/)

### Feel free to ask any questions.

## LICENSE

```
The MIT License (MIT)

Copyright (c) 2015 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
