package amatkivskiy.gitter.rx.sdk.converter;

import amatkivskiy.gitter.rx.sdk.model.response.UserResponse;
import com.google.gson.*;

import java.lang.reflect.Type;

public class UserConverter implements JsonDeserializer<UserResponse> {
  @Override
  public UserResponse deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject element = jsonElement.getAsJsonArray().get(0).getAsJsonObject();

    String id = element.get("id").getAsString();
    int v = element.get("v").getAsInt();
    String username = element.get("username").getAsString();
    String avatarUrlSmall = element.get("avatarUrlSmall").getAsString();
    String avatarUrlMedium = element.get("avatarUrlMedium").getAsString();
    String gv = element.get("gv").getAsString();
    String displayName = element.get("displayName").getAsString();
    String url = element.get("url").getAsString();

    return new UserResponse(id, v, username, avatarUrlSmall, gv, displayName, url, avatarUrlMedium);
  }
}
