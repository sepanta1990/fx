package com.paf.exercise.exercise.dto.telegram;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendMessageRequest {
    @JsonProperty("chat_id")
    private String chatId;
    private String text;
    @JsonProperty("reply_to_message_id")
    private Long replyToMessageId;

    public SendMessageRequest() {
    }

    public SendMessageRequest(String chatId, String text, Long replyToMessageId) {
        this.chatId = chatId;
        this.text = text;
        this.replyToMessageId = replyToMessageId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(Long replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }
}
