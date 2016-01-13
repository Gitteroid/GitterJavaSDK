package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.google.gson.Gson;

import com.amatkivskiy.gitter.sdk.async.faye.model.MessageEvent;

import static com.amatkivskiy.gitter.sdk.async.faye.FayeConstants.FayeChannels.ROOM_MESSAGES_CHANNEL_TEMPLATE;

public abstract class RoomMessagesChannel extends AbstractChannelListener<MessageEvent> {
  private String roomId;

  public RoomMessagesChannel(String roomId) {
    super(new Gson());
    this.roomId = roomId;
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
