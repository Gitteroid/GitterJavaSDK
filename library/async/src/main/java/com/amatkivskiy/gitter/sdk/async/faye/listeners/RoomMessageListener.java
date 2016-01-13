package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.google.gson.Gson;

import com.amatkivskiy.gitter.sdk.async.faye.model.MessageEvent;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.ROOM_MESSAGES_CHANNEL_TEMPLATE;

public abstract class RoomMessageListener extends BaseChannelListener<MessageEvent> {
  private String roomId;

  public RoomMessageListener(String roomId) {
    super(new Gson());
    this.roomId = roomId;
  }

  public String getRoomId() {
    return roomId;
  }

  @Override
  public String getChannel() {
    return String.format(ROOM_MESSAGES_CHANNEL_TEMPLATE, roomId);
  }

  @Override
  protected Class<MessageEvent> getParameterClass() {
    return MessageEvent.class;
  }
}
