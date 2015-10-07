# GitterRxJavaSDK

[ ![Download](https://api.bintray.com/packages/amatkivskiy/maven/gitter.rx.sdk/images/download.svg) ](https://bintray.com/amatkivskiy/maven/gitter.rx.sdk/_latestVersion)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GitterRxJavaSDK-green.svg?style=flat)](https://android-arsenal.com/details/1/2599)

[![Join the chat at https://gitter.im/Gitteroid/GitterRxJavaSDK](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Gitteroid/GitterRxJavaSDK?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Gitter.im Java SDK that facilitates communication with Gitter API. Uses RxJava and Retrofit.

## Setup

Add gradle dependency:
```
repositories {
      jcenter()
}

dependencies {
      compile 'com.github.amatkivskiy:gitter.rx.sdk:1.0.0'
}
```

## Features

- Authentication

*Rooms resource*
- List rooms
- Room users
- Channels
- Join a room

*User resource*
- Current user
- User rooms
- User orgs
- User repos
- User channels

*Messages resource*
- Unread items
- List messages
- Send a message
- Update a message

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

You can see some code samples [here](https://github.com/Gitteroid/GitterRxJavaSDK/blob/master/samples/src/main/java/com/amatkivskiy/gitter/rx/sdk/samples/Samples.java)

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
