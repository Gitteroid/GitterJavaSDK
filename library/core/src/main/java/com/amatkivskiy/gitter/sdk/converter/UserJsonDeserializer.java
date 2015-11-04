package com.amatkivskiy.gitter.sdk.converter;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.google.gson.*;

import java.lang.reflect.Type;

public class UserJsonDeserializer implements JsonDeserializer<UserResponse> {
  @Override
  public UserResponse deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject element = null;
    if (jsonElement.isJsonArray()) {
      element = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
    } else {
      element = jsonElement.getAsJsonObject();
    }

    String id = element.get("id") != null ? element.get("id").getAsString() : null;
    Integer v = element.get("v") != null ? element.get("v").getAsInt() : null;
    String username = element.get("username") != null ? element.get("username").getAsString() : null;
    String avatarUrlSmall = element.get("avatarUrlSmall") != null ? element.get("avatarUrlSmall").getAsString() : null;
    String avatarUrlMedium = element.get("avatarUrlMedium") != null ? element.get("avatarUrlMedium").getAsString() : null;
    String gv = element.get("gv") != null ? element.get("gv").getAsString() : null;
    String displayName = element.get("displayName") != null ? element.get("displayName").getAsString() : null;
    String url = element.get("url") != null ? element.get("url").getAsString() : null;

    return new UserResponse(id, v, username, avatarUrlSmall, gv, displayName, url, avatarUrlMedium);
  }
}
