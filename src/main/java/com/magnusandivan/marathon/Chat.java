package com.magnusandivan.marathon;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Chat {
    @JsonIgnore
    UUID id;
    List<UUID> userIds;
    @JsonIgnore
    List<MessageWebSocketHandler.IndividualHandler> activeUsers;
    @JsonIgnore
    // This will be a "circular array", meaning that the start is constantly moving, and we are overwriting previous messages. This is so we don't have to constantly shuffle messages around when removing them
    Message[] recentMessages;
    @JsonIgnore
    int recentMessagesStart;

    int currentMessageIndex;

    public Chat() {

    }
    public void writeNewMessages(Message[] messages) {
        int messageIndex = recentMessagesStart;
        // We want the last messages in the array to be the first to be rendered
        for (int i = 0;i < messages.length;i++) {
            messageIndex = (messageIndex - 1) % recentMessages.length; // This means it will be put back to the end of the list
            recentMessages[messageIndex] = messages[i];
        }
        recentMessagesStart = messageIndex;
    }
    public Message[] getRecentMessages() {
        Message[] messages = new Message[recentMessages.length];
        for (int i = 0;i < messages.length;i++) {
            messages[i] = recentMessages[(recentMessagesStart + i) % messages.length];
        }
        return messages;
    }
}
