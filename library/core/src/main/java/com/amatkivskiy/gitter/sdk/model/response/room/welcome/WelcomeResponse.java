package com.amatkivskiy.gitter.sdk.model.response.room.welcome;

public class WelcomeResponse {
  public final String html;
  public final String text;
  public final String plainText;

  public WelcomeResponse(String html, String text, String plainText) {
    this.html = html;
    this.text = text;
    this.plainText = plainText;
  }

  @Override
  public String toString() {
    return "{" +
        "html='" + html + '\'' +
        ", text='" + text + '\'' +
        ", plainText='" + plainText + '\'' +
        '}';
  }
}
