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
     * Writes messages to the recentMessages location in memory, and updates the
     * number of messages, the latter of which can be written with this chat, but it
     * doesn't save either immediately
     * 
     * @param messages The messages to store
     */
    public void writeNewMessages(Message[] messages) {
        int start = recentMessagesStart + recentMessagesCount;
        for (int i = 0; i < messages.length; i++) {
            recentMessages[(start + i) % recentMessages.length] = messages[i];
        }
        if (messages.length + recentMessagesCount > recentMessages.length) {
            recentMessagesStart = (recentMessagesStart + messages.length + recentMessagesCount - recentMessages.length)
                    % recentMessages.length;
        }
        recentMessagesCount = Integer.min(recentMessagesCount + messages.length, recentMessages.length);
        currentMessageIndex += messages.length;
    }

    /**
     * Returns the most recent messages, up to a limit. However, if there are very
     * few messages, some elements of the array returned may be null
     * 
     * @return The messages
     */
    public Message[] getRecentMessages() {
        Message[] messages = new Message[recentMessagesCount];
        for (int i = 0; i < recentMessagesCount; i++) {
            messages[i] = recentMessages[(recentMessagesStart + i) % recentMessages.length];
        }
        return messages;
    }
}
