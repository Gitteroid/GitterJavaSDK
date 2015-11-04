package com.amatkivskiy.gitter.sdk.util;

public class StringUtils {
  public static boolean checkIfValidMessageJson(String json) {
    return json != null && json.contains("{") && json.contains("}");
  }
}
