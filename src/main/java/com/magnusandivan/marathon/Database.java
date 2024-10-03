package com.magnusandivan.marathon;

import java.util.UUID;

import com.magnusandivan.marathon.changable_implementations.Message;

// The api our "database" should provide.
public interface Database {
    Chat getChat(UUID chatId);

    Message[] getMessages(UUID chatId, int startIndex, int numMessages);

    UserInfo getUser(UUID userId);

    void insertMessages(UUID chatId, Message[] messages);

    void insertChat(Chat chat);

    // This will add some users to a chat's info but not add the chat to the users'
    // info
    void addUsersToChat(UUID chat, UUID[] users);

    void insertUser(UserInfo user);

    // This will add some chats to the user's info but not add the user to the
    // chats' info
    void addUserToChats(UUID user, UUID[] chats);

    // This is where unused items in the cache or whatnot should be removed
    void garbageCollection();

    // This should be used to write out any pending changes, if they haven't already
    // been written
    public void flush();
}
