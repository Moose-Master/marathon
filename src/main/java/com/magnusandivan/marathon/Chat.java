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
}
