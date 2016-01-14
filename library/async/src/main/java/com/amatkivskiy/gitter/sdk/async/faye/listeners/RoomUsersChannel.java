package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.google.gson.Gson;

import com.amatkivskiy.gitter.sdk.async.faye.model.UserEvent;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.ROOM_USERS_CHANNEL_TEMPLATE;

/**
 * Channel for users changes in the room like adding new user or removing old one.
 */
public abstract class RoomUsersChannel extends AbstractChannelListener<UserEvent> {
  private String roomId;

  public RoomUsersChannel(String roomId) {
    super(new Gson());
    this.roomId = roomId;
  }

  @Override
  public String getChannel() {
    return String.format(ROOM_USERS_CHANNEL_TEMPLATE, roomId);
  }

  @Override
  protected Class<UserEvent> getParameterClass() {
    return UserEvent.class;
  }
}
