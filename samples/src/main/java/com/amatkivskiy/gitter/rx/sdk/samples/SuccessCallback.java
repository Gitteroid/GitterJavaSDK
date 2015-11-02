package com.amatkivskiy.gitter.rx.sdk.samples;

import retrofit.Callback;
import retrofit.RetrofitError;

public abstract class SuccessCallback<T> implements Callback<T> {
  @Override
  public void failure(RetrofitError error) {
    System.err.println("SuccessCallback.failure");
    error.printStackTrace();
  }
}
