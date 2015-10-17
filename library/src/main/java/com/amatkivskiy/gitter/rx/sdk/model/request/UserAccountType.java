package com.amatkivskiy.gitter.rx.sdk.model.request;

public enum UserAccountType {
  Github("github"),
  Gitter("gitter");

  private String value;

  UserAccountType(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
