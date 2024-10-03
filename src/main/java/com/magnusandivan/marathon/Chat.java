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

    public int currentMessageIndex;

    public Chat() {

    }

    public void writeNewMessages(Message[] messages) {
        int messageIndex = recentMessagesStart;
        // We want the last messages in the array to be the first to be rendered
        for (int i = 0; i < messages.length; i++) {
            messageIndex = Math.floorMod(messageIndex - 1, recentMessages.length); // Floor mod behaves like you would
                                                                                   // expect mod to, it makes sure its
                                                                                   // always [0,x) instead of (-x, x)

            recentMessages[messageIndex] = messages[i];
        }
        recentMessagesStart = messageIndex;
    }

    public Message[] getRecentMessages() {
        Message[] messages = new Message[recentMessages.length];
        for (int i = 0; i < messages.length; i++) {
            Message m = recentMessages[(recentMessagesStart + i) % messages.length];
            if (m == null)
                break;
            messages[i] = m;
        }
        return messages;
    }
}
