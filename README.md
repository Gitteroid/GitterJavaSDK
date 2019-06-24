
[![Build Status](https://travis-ci.org/Gitteroid/GitterJavaSDK.svg?branch=dev)](https://travis-ci.org/Gitteroid/GitterJavaSDK) 
# Gitter.im Java SDK

:+1: **_Fully compatible with Android_**

Async : [ ![Download](https://api.bintray.com/packages/amatkivskiy/maven/gitter.sdk.async/images/download.svg) ](https://bintray.com/amatkivskiy/maven/gitter.sdk.async/_latestVersion)

Sync : [ ![Download](https://api.bintray.com/packages/amatkivskiy/maven/gitter.sdk.async/images/download.svg) ](https://bintray.com/amatkivskiy/maven/gitter.sdk.sync/_latestVersion)

Rx : [ ![Download](https://api.bintray.com/packages/amatkivskiy/maven/gitter.sdk.async/images/download.svg) ](https://bintray.com/amatkivskiy/maven/gitter.sdk.rx/_latestVersion) [![Coverage Status](https://coveralls.io/repos/github/Gitteroid/GitterJavaSDK/badge.svg?branch=feature%2Ftesting_plus_ci)](https://coveralls.io/github/Gitteroid/GitterJavaSDK?branch=feature%2Ftesting_plus_ci)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GitterJavaSDK-green.svg?style=flat)](http://android-arsenal.com/details/1/2599)

[![Join the chat at https://gitter.im/Gitteroid/GitterRxJavaSDK](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Gitteroid/GitterJavaSDK?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Gitter.im Java SDK that facilitates communication with Gitter API, Gitter Streaming API, Gitter Faye API.

It provides three approaches to work with API:
- RxJava approach;
- Async (callback) approach.
- Sync approach.

## Table of content
- [Features](#Features)
- [Release Notes](#ReleaseNotes)
- [Setup](#Setup)
- [Description](#Description)
- [Streaming](#Streaming)
- [Faye](#Faye)
- [Samples](#Samples)

### <a name="Setup">**Setup**
Add gradle dependency:

For RxJava:
```groovy
repositories {
      jcenter()
}

dependencies {
      compile 'com.github.amatkivskiy:gitter.sdk.rx:1.6.1'
}
```

For async:
```groovy
repositories {
      jcenter()
}

dependencies {
      compile 'com.github.amatkivskiy:gitter.sdk.async:1.6.1'
}
```

For sync:
```groovy
repositories {
      jcenter()
}

dependencies {
      compile 'com.github.amatkivskiy:gitter.sdk.sync:1.6.1'
}
```

### <a name="ReleaseNotes">**Release notes**
- **1.6.1** (04.01.2018)
    - *Rx,Async,Sync:*
        - Add `getRoomIdByUri` to room API
	- Update dependencies used in the SDK
	
- **1.6.0** (12.01.2017)
    - *RoomResponse:*
        - Remove `favouriteOrder`
        - Add `avatarUrl`
        - Add `groupId`
        - Add `public`
        - Add `activity`
        - Add `premium`

    - *UserResponse:*
        - Add `avatarUrl`
        - Add `role`
        - Add `staff`

    - *Rx,Async,Sync:*
        - Add `delete` room API
        - Remove channels API
        - Fix `joinRoom` API
        - Add `getRoomById` API
        - Add `getBannedusers` API
        - Add `banUser` API
        - Add `unBanUser` API
        - Add Welcome API support

    - *Faye*
        - Update OkHttp to version 3.5.0
- **1.5.1** (07.05.2016)
    - Updated `RoomRepsonse` data structure.
    - Added `aroundId` and `q` params for chat messages request.
    - Added `Update room` request.
    - Fixed `Issue` data structure type.
    - Faye: added ability to set custom OkHttpClient.
    - Faye: changed `onSubscribed()` params, no it passes messages snapshots.
- **1.5** (14.01.2016)
    - Added faye api support.
    - Added room events streaming api support.
- **1.4**
    - Refactored library structure
    - Added async api support.
    - Added async api samples.
    - Added sync api support.
    - Added sync api samples.
- **1.2.1**
    - Added ability to retrieve unread messages.
- **1.2.0**
    - Added ability to search users
    - Added ability to search rooms
    - Added ability to leave room
- **1.1.0**
    - Added room messages streaming API.

### <a name="Features">**Features**

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
- Room events stream

*:heavy_exclamation_mark: Faye API (Avalible only in Async part.*)
- Room messages events
- Room user presence events
- Room user managment events

### <a name="Description">**Description**

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

- Sync:
```java
SyncGitterAuthenticationClient authenticationClient = new SyncGitterAuthenticationClient.Builder()
        .build();

AccessTokenResponse accessTokenResponse = authenticationClient.getAccessToken(code);
System.out.println("Access token = " + accessTokenResponse.accessToken);

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

- Sync:
```
SyncGitterApiClient client = new SyncGitterApiClient.Builder()
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

- Sync:
```java
UserResponse userResponse = client.getCurrentUser();
System.out.println("userResponse.displayName = " + userResponse.displayName);
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

- Sync:
```java
ChatMessagesRequestParams params = new ChatMessagesRequestParamsBuilder().limit(20).build();
String roomId = "room_id";

List<MessageResponse> messages = client.getRoomMessages(roomId, params);
System.out.println("Received " + messages.size() + " messages");
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
- Sync:
```java
List<RoomResponse> rooms = client.getUserChannels("user_id");
System.out.println("Received " + rooms.size() + " rooms");
```

### <a name="Streaming">**How to get streaming data from Gitter Streaming API**

### :heavy_exclamation_mark: Please don't set any log level for *RxGitterStreamingApiClient* as it blocks the stream.
:heavy_exclamation_mark: If you get `java.net.SocketTimeoutException: Read timed out` try to encrease `ReadTimeout` in your `retrofit.client.Client` and spicify this client for `GutterApiClient` (`withClient()`).

```java
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

or 

```java
RxGitterStreamingApiClient client = new RxGitterStreamingApiClient.Builder()
        .withAccountToken("user_access_token")
        .build();

String roomId = "room_id";

client.getRoomEventsStream(roomId).subscribe(new Action1<RoomEvent>() {
	@Override
	public void call(RoomEvent event) {
		System.out.println(event.sent);
	        System.out.println(event.meta);
	}
});
```

### <a name="Faye">**How to work with Gitter Faye API**

1 Setup ```AsyncGitterFayeClient```:

```java
AsyncGitterFayeClient client = new AsyncGitterFayeClientBuilder()
        .withAccountToken("account_token")
        .withOnDisconnected(new DisconnectionListener() {
          @Override
          public void onDisconnected() {
            // Client has disconnected. You can reconnect it here.
          }
        })
        .withOkHttpClient(new OkHttpClient())
        .build();
```

2 Connect it to the server:

```java
client.connect(new ConnectionListener() {
      @Override
      public void onConnected() {
        // Client is ready. Subscribe to channels you are intereseted in.
      }
    });
```

3 Subscribe to desighed channel:

```java
client.subscribe(new RoomMessagesChannel("room_id") {
          @Override
          public void onMessage(String channel, MessageEvent message) {
            // Yeah, you've got message here.
          }
});
```

or 

```java
client.subscribe(new RoomUserPresenceChannel("room_id") {
          @Override
          public void onMessage(String channel, UserPresenceEvent message) {
            // User is active or not.
          }
});
```

or 

```java
client.subscribe(new RoomUsersChannel("room_id") {
          @Override
          public void onMessage(String channel, UserEvent message) {
            // User left ot joined the room.
          }
});
```

or define your custom channel: 

```java
client.subscribe("channel_name",new ChannelListener(){
@Override
public void onMessage(String channel,JsonObject message){
//        Handle message here.
    }

@Override
public void onFailed(String channel,Exception ex){
    }

@Override
public void onSubscribed(String channel, List<MessageResponse> messagesSnapshot){
    }

@Override
public void onUnSubscribed(String channel){
    }
    });
```

You can unsubscribe from channel: 


```java
client.unSubscribe("channel_name");
```

or 

```java
RoomMessagesChannel channel = new RoomMessagesChannel("room_id") {
        @Override
        public void onMessage(String channel, MessageEvent message) {
        }
};
client.subscribe(channel);

client.unSubscribe(channel);
```

Finally when your are finished with client, you need to call:

```java
client.disconnect();
```

Thats all =).

### <a name="Samples">**Samples**

You can see some code samples [here](https://github.com/Gitteroid/GitterJavaSDK/tree/master/samples/src/main/java/com/amatkivskiy/gitter/sdk/samples)

### Feel free to ask any questions.

## LICENSE

```
The MIT License (MIT)

Copyright (c) 2017 

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
