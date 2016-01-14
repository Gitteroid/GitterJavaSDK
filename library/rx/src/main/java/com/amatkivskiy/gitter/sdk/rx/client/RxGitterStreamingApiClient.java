package com.amatkivskiy.gitter.sdk.rx.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.api.builder.GitterApiBuilder;
import com.amatkivskiy.gitter.sdk.converter.UserJsonDeserializer;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.rx.api.RxGitterStreamingApi;
import com.amatkivskiy.gitter.sdk.rx.streaming.OnSubscribeBufferedReader;
import com.amatkivskiy.gitter.sdk.rx.streaming.model.RoomEvent;
import com.amatkivskiy.gitter.sdk.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Func1;

public class RxGitterStreamingApiClient {
  private RxGitterStreamingApi api;
  private Gson gson;

  public RxGitterStreamingApiClient(RxGitterStreamingApi api, Gson gson) {
    this.api = api;
    this.gson = gson;
  }

  public Observable<MessageResponse> getRoomMessagesStream(String roomId) {
    return api.getRoomMessagesStream(roomId).flatMap(new Func1<Response, Observable<String>>() {
      @Override
      public Observable<String> call(Response response) {
        try {
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));
          return Observable.create(new OnSubscribeBufferedReader(bufferedReader));
        } catch (IOException e) {
          return Observable.error(e);
        }
      }
    }).filter(new Func1<String, Boolean>() {
      @Override
      public Boolean call(String s) {
//        This check is required because server sometimes return empty string or string with newline character.
        return StringUtils.checkIfValidMessageJson(s);
      }
    }).map(new Func1<String, MessageResponse>() {
      @Override
      public MessageResponse call(String s) {
        return gson.fromJson(s, MessageResponse.class);
      }
    });
  }

  public Observable<RoomEvent> getRoomEventsStream(String roomId) {
    return api.getRoomEventsStream(roomId).flatMap(new Func1<Response, Observable<String>>() {
      @Override
      public Observable<String> call(Response response) {
        try {
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));
          return Observable.create(new OnSubscribeBufferedReader(bufferedReader));
        } catch (IOException e) {
          return Observable.error(e);
        }
      }
    }).filter(new Func1<String, Boolean>() {
      @Override
      public Boolean call(String s) {
//        This check is required because server sometimes return empty string or string with newline character.
        return StringUtils.checkIfValidMessageJson(s);
      }
    }).map(new Func1<String, RoomEvent>() {
      @Override
      public RoomEvent call(String s) {
        return gson.fromJson(s, RoomEvent.class);
      }
    });
  }

  public static class Builder extends GitterApiBuilder<Builder, RxGitterStreamingApiClient> {

    protected String getFullEndpointUrl() {
      return Constants.GitterEndpoints.GITTER_STREAMING_API_ENDPOINT + "/" + apiVersion + "/";
    }

    @Override
    public RxGitterStreamingApiClient build() {
      prepareDefaultBuilderConfig();

      Gson gson = new GsonBuilder()
          .registerTypeAdapter(UserResponse.class, new UserJsonDeserializer())
          .create();
      restAdapterBuilder.setConverter(new GsonConverter(gson));

      RxGitterStreamingApi api = restAdapterBuilder.build().create(RxGitterStreamingApi.class);
      return new RxGitterStreamingApiClient(api, gson);
    }
  }
}
