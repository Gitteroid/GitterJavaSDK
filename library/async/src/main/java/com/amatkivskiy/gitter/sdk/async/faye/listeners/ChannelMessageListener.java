package com.amatkivskiy.gitter.sdk.async.faye.listeners;

import com.amatkivskiy.gitter.sdk.async.faye.model.MessageEvent;
import com.google.gson.Gson;

public class ChannelMessageListener extends BaseChannelListener<MessageEvent> {
  public final String ChatMessagesEndpointTemple = "/api/v1/rooms/%s/chatMessages";

  private String roomId;

  public ChannelMessageListener(String roomId) {
    super(new Gson());
    this.roomId = roomId;
  }

  public String getRoomId() {
    return roomId;
  }

  @Override
  public String getChannel() {
    return String.format(ChatMessagesEndpointTemple, roomId);
  }

  @Override
  protected Class<MessageEvent> getParameterClass() {
    return MessageEvent.class;
  }

  @Override
  public void onMessage(String channel, MessageEvent message) {
  }

  @Override
  public void onUnSubscribed(String channel) {

  }
}
