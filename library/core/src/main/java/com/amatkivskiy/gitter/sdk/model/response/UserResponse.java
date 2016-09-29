package com.amatkivskiy.gitter.sdk.model.response;

public class UserResponse {
  public final String id;
  public final Integer v;
  public final String username;
  public final String displayName;
  public final String avatarUrl;
  public final String avatarUrlSmall;
  public final String avatarUrlMedium;
  public final Role role;
  public final boolean staff;
  public final String gv;
  public final String url;

  public UserResponse(String id, Integer v, String username, String avatarUrl, String avatarUrlSmall, String gv, String displayName, String url, String avatarUrlMedium, Role role, boolean staff) {
    this.id = id;
    this.v = v;
    this.username = username;
    this.avatarUrl = avatarUrl;
    this.avatarUrlSmall = avatarUrlSmall;
    this.gv = gv;
    this.displayName = displayName;
    this.url = url;
    this.avatarUrlMedium = avatarUrlMedium;
    this.role = role;
    this.staff = staff;
  }

  @Override
  public String toString() {
    return "UserResponse{" +
        "id='" + id + '\'' +
        ", v=" + v +
        ", username='" + username + '\'' +
        ", displayName='" + displayName + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", avatarUrlSmall='" + avatarUrlSmall + '\'' +
        ", avatarUrlMedium='" + avatarUrlMedium + '\'' +
        ", role=" + role +
        ", staff=" + staff +
        ", gv='" + gv + '\'' +
        ", url='" + url + '\'' +
        '}';
  }
}
