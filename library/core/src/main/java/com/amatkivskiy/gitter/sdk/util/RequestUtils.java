package com.amatkivskiy.gitter.sdk.util;

import com.amatkivskiy.gitter.sdk.Constants;
import com.amatkivskiy.gitter.sdk.model.request.ChatMessagesRequestParams;

import java.util.HashMap;

public class RequestUtils {
  public static HashMap<String, String> convertChatMessagesParamsToMap(
      ChatMessagesRequestParams params) {
    HashMap<String, String> options = new HashMap<>();
    if (params != null) {
      if (params.limit != null) {
        options.put(Constants.GitterRequestParams.LIMIT_PARAM, String.valueOf(params.limit.intValue()));
      }

      if (params.afterId != null) {
        options.put(Constants.GitterRequestParams.AFTER_ID_PARAM, params.afterId);
      }

      if (params.beforeId != null) {
        options.put(Constants.GitterRequestParams.BEFORE_ID_PARAM, params.beforeId);
      }

      if (params.skipCount != null) {
        options.put(Constants.GitterRequestParams.SKIP_PARAM, String.valueOf(params.skipCount.intValue()));
      }

      if (params.aroundId != null) {
        options.put(Constants.GitterRequestParams.AROUND_ID_PARAM, params.aroundId);
      }

      if (params.searchQuery != null) {
        options.put(Constants.GitterRequestParams.SEARCH_QUERY_PARAM, params.searchQuery);
      }
    }

    return options;
  }
}
