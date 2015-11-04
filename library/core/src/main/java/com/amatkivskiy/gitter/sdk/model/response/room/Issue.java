package com.amatkivskiy.gitter.sdk.model.response.room;

import java.util.List;

public class Issue {
  public final List<String> numbers;

  public Issue(List<String> numbers) {
    this.numbers = numbers;
  }
}
