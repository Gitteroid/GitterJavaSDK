package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.google.gson.Gson;

import com.amatkivskiy.gitter.sdk.async.faye.model.UserEvent;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.ROOM_USERS_CHANNEL_TEMPLATE;

public abstract class RoomUsersListener extends BaseChannelListener<UserEvent> {
  private String roomId;

  public RoomUsersListener(String roomId) {
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
