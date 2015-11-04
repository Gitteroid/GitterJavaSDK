package com.amatkivskiy.gitter.sdk.samples;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EmptyCallback<T> implements Callback<T> {
  @Override
  public void success(T t, Response response) {

  }

  @Override
  public void failure(RetrofitError error) {

  }
}
