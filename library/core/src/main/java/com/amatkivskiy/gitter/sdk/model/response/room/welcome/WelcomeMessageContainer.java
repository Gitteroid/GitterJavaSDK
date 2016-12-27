package com.amatkivskiy.gitter.sdk.model.response.room.welcome;

/**
 * This class is just a wrapper for the {@link WelcomeResponse} because of the backend response
 * format for the update welcome message request.
 */
public class WelcomeMessageContainer {
  public final WelcomeResponse welcomeMessage;

  public WelcomeMessageContainer(WelcomeResponse welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
  }
}
