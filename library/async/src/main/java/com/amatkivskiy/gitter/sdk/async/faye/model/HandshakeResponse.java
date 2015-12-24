package com.amatkivskiy.gitter.sdk.async.faye.model;

import java.util.ArrayList;
import java.util.List;

public class HandshakeResponse extends BaseFayeResponse {
  public final boolean successful;
  public final String version;
  public final List<String> supportedConnectionTypes = new ArrayList<String>();
  public final Ext ext;

  public HandshakeResponse(String channel, String clientId, boolean successful, String version, Ext ext) {
    super(channel, clientId);
    this.successful = successful;
    this.version = version;
    this.ext = ext;
  }
}