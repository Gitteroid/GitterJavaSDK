package com.amatkivskiy.gitter.sdk.rx.streaming;

import java.io.BufferedReader;
import java.io.IOException;

import rx.Observer;
import rx.observables.SyncOnSubscribe;

public class OnSubscribeBufferedReader extends SyncOnSubscribe<BufferedReader, String> {

  private final BufferedReader reader;

  public OnSubscribeBufferedReader(BufferedReader reader) {
    this.reader = reader;
  }

  @Override
  protected BufferedReader generateState() {
    return reader;
  }

  @Override
  protected BufferedReader next(BufferedReader state, Observer<? super String> observer) {
    try {
      String line = reader.readLine();
      if (line == null) {
        observer.onCompleted();
      } else {
        observer.onNext(line);
      }
    } catch (IOException e) {
      observer.onError(e);
    }

    return reader;
  }

  @Override
  protected void onUnsubscribe(BufferedReader state) {
    if (state != null) {
      try {
        state.close();
      } catch (IOException e) {
//        Ignore this exception
      }
    }
  }
}