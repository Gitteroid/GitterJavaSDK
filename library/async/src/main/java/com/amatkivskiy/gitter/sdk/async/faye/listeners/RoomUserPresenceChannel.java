package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.google.gson.Gson;

import com.amatkivskiy.gitter.sdk.async.faye.model.UserPresenceEvent;
import com.amatkivskiy.gitter.sdk.async.faye.model.UserPresenceEvent.PresenceStatus;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.ROOM_USERS_PRESENCE_CHANNEL_TEMPLATE;

/**
 * Use this channel to catch user {@link PresenceStatus#In} and {@link PresenceStatus#Out} events in the room.
  */
public abstract class RoomUserPresenceChannel extends BaseChannelListener<UserPresenceEvent> {
  private String roomId;

  public RoomUserPresenceChannel(String roomId) {
    super(new Gson());
    this.roomId = roomId;
  }

  @Override
  public String getChannel() {
    return String.format(ROOM_USERS_PRESENCE_CHANNEL_TEMPLATE, roomId);
  }

  @Override
  protected Class<UserPresenceEvent> getParameterClass() {
    return UserPresenceEvent.class;
  }
}
