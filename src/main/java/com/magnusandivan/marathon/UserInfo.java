package com.magnusandivan.marathon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserInfo {
    @JsonIgnore
    UUID id;
    @JsonIgnore
    // This will be null unless the user is online
    MessageWebSocketHandler.IndividualHandler activeUser;
    String name;
    // The chats the user is in
    List<UUID> chatIds;

    public UserInfo() {
        
    }
    public UserInfo(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.activeUser = null;
        this.chatIds = new ArrayList<>();
    }
}
