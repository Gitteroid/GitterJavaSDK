package com.amatkivskiy.gitter.sdk.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.amatkivskiy.gitter.sdk.model.response.Role;
import com.amatkivskiy.gitter.sdk.model.response.UserResponse;

import java.lang.reflect.Type;

public class UserJsonDeserializer implements JsonDeserializer<UserResponse> {
  @Override
  public UserResponse deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext deserializer) throws JsonParseException {
    JsonObject element;
    if (jsonElement.isJsonArray()) {
      element = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
    } else {
      element = jsonElement.getAsJsonObject();
    }

    String id = element.get("id") != null ? element.get("id").getAsString() : null;
    Integer v = element.get("v") != null ? element.get("v").getAsInt() : null;
    String username = element.get("username") != null ? element.get("username").getAsString() : null;
    String avatarUrlSmall = element.get("avatarUrlSmall") != null ? element.get("avatarUrlSmall").getAsString() : null;
    String avatarUrl = element.get("avatarUrl") != null ? element.get("avatarUrl").getAsString() : null;
    String avatarUrlMedium = element.get("avatarUrlMedium") != null ? element.get("avatarUrlMedium").getAsString() : null;
    String gv = element.get("gv") != null ? element.get("gv").getAsString() : null;
    String displayName = element.get("displayName") != null ? element.get("displayName").getAsString() : null;
    String url = element.get("url") != null ? element.get("url").getAsString() : null;
    boolean staff = element.get("staff") != null && element.get("staff").getAsBoolean();

    Role role;
    if (element.get("role") != null) {
      role = deserializer.deserialize(element.get("role"), Role.class);
      if (role == null) {
        role = Role.STANDARD;
      }
    } else {
      role = Role.STANDARD;
    }

    return new UserResponse(id, v, username, avatarUrl, avatarUrlSmall, gv, displayName, url, avatarUrlMedium, role, staff);
  }
}
