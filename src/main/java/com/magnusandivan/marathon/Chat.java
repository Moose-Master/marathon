package com.magnusandivan.marathon;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magnusandivan.marathon.api.ConnectionHandler;
import com.magnusandivan.marathon.changable_implementations.Message;

public class Chat {
    @JsonIgnore
    public UUID id;
    public List<UUID> userIds;
    @JsonIgnore
    public List<ConnectionHandler> activeUsers;
    // This will be a "circular array", meaning that the start is constantly moving,
    // and we are overwriting previous messages. This is so we don't have to
    // constantly shuffle messages around when removing them
    @JsonIgnore
    public Message[] recentMessages;
    @JsonIgnore
    public int recentMessagesStart;
    @JsonIgnore
    public int recentMessagesCount;

    public int currentMessageIndex;

    public Chat() {

    }

    /**
     * Writes messages to the recentMessages location in memory, but doesn't save
     * them to the database
     * 
     * @param messages
     */
    public void writeNewMessages(Message[] messages) {
        int messageIndex = (recentMessagesStart + recentMessagesCount) % recentMessages.length;
        for (int i = 0; i < messages.length; i++) {
            messageIndex = (messageIndex + 1) % recentMessages.length;
            if (recentMessages[messageIndex] != null) {
                recentMessagesStart = (messageIndex + 1) % recentMessages.length;
            }
            recentMessages[messageIndex] = messages[i];
        }
        currentMessageIndex += messages.length;
    }

    /**
     * Returns the most recent messages, up to a limit. However, if there are very
     * few messages, some elements of the array returned may be null
     * 
     * @return The messages
     */
    public Message[] getRecentMessages() {
        Message[] messages = new Message[recentMessages.length];
        for (int i = 0; i < recentMessagesCount; i++) {
            Message m = recentMessages[(recentMessagesStart + i) % recentMessages.length];
            if (m == null)
                break;
            messages[i] = m;
        }
        return messages;
    }
}
