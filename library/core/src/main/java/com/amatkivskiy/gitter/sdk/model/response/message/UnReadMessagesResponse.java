package com.amatkivskiy.gitter.sdk.model.response.message;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnReadMessagesResponse {
    @SerializedName("chat")
    public final List<String> unReadMessagesIds;
    @SerializedName("mention")
    public final List<String> unReadMentionedMessagesIds;

    public UnReadMessagesResponse(List<String> unReadMessagesIds, List<String> unReadMentionedMessagesIds) {
        this.unReadMessagesIds = unReadMessagesIds;
        this.unReadMentionedMessagesIds = unReadMentionedMessagesIds;
    }

    @Override
    public String toString() {
        return "UnReadMessagesResponse{" +
            "unReadMessagesIds=" + unReadMessagesIds +
            ", unReadMentionedMessagesIds=" + unReadMentionedMessagesIds +
            '}';
    }
}
