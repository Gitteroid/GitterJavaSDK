package com.amatkivskiy.gitter.sdk.rx.streaming;

import java.io.BufferedReader;
import java.io.IOException;

import rx.Subscriber;
import rx.observables.AbstractOnSubscribe;

public class OnSubscribeBufferedReader extends AbstractOnSubscribe<String, BufferedReader> {

  private final BufferedReader reader;

  public OnSubscribeBufferedReader(BufferedReader reader) {
    this.reader = reader;
  }

  @Override
  protected BufferedReader onSubscribe(Subscriber<? super String> subscriber) {
    return reader;
  }

  @Override
  protected void next(SubscriptionState<String, BufferedReader> state) {
    BufferedReader reader = state.state();
    try {
      String line = reader.readLine();
      if (line == null) {
        state.onCompleted();
      } else {
        state.onNext(line);
      }
    } catch (IOException e) {
      state.onError(e);
    }
  }

  @Override
  protected void onTerminated(BufferedReader state) {
    super.onTerminated(state);

    if (state != null) {
      try {
        state.close();
      } catch (IOException e) {
//        Ignore this exception
      }
    }
  }
}