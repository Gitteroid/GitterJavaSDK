package amatkivskiy.gitter.rx.sdk.converter;

import amatkivskiy.gitter.rx.sdk.model.response.UserResponse;
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

    String id = element.get("id").getAsString();

    Integer v = null;
    if (element.get("v") != null) {
      v = element.get("v").getAsInt();
    }

    String username = element.get("username").getAsString();
    String avatarUrlSmall = element.get("avatarUrlSmall").getAsString();
    String avatarUrlMedium = element.get("avatarUrlMedium").getAsString();

    String gv = null;
    if (element.get("gv") != null) {
      gv = element.get("gv").getAsString();
    }

    String displayName = element.get("displayName").getAsString();
    String url = element.get("url").getAsString();

    return new UserResponse(id, v, username, avatarUrlSmall, gv, displayName, url, avatarUrlMedium);
  }
}
