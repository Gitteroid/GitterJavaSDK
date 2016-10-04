package com.amatkivskiy.gitter.sdk.model.response.group;

public class BackedBy {
  public final String linkPath;
  public final Type type;

  public BackedBy(String linkPath, Type type) {
    this.linkPath = linkPath;
    this.type = type;
  }

  public enum Type {
    ONE_TO_ONE,
    GH_REPO,
    GH_ORG,
    GH_USER
  }
}
