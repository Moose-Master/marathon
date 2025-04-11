package com.magnusandivan.marathon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magnusandivan.marathon.api.ConnectionHandler;

public class UserInfo {
    @JsonIgnore
    public UUID id;
    // This will be null unless the user is online
    @JsonIgnore
    public ConnectionHandler activeUser;
    public String name;
    // Should be all lowercase
    public String username;
    // The chats the user is in
    public List<UUID> chatIds;

    public UserInfo() {

    }

    public UserInfo(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.activeUser = null;
        this.chatIds = new ArrayList<>();
    }
}
