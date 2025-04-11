package com.magnusandivan.marathon.api;

import java.util.UUID;

import com.magnusandivan.marathon.Chat;
import com.magnusandivan.marathon.UserInfo;
import com.magnusandivan.marathon.changable_implementations.Message;

// The api our "database" should provide.
public interface Database {
    /**
     * Returns the chat id of the global chat, which corresponds to some kind of
     * zero representation
     * 
     * @return The global chat id
     */
    UUID globalChatId();

    /**
     * Get info about a chat, note that in order to save changes you must manually
     * call
     * <code>insertChat()</code>
     * 
     * @param chatId The id of the chat
     * @return The chat info
     */
    Chat getChat(UUID chatId);

    /**
     * Get messages in a range from a chat. Note that this is an expensive
     * operation, particularly on chats with long message histories
     * 
     * @param chatId      The id of the chat
     * @param startIndex  The first message
     * @param numMessages The total number of messages to read
     * @return An array of messages, the first sent first in the array
     */
    Message[] getMessages(UUID chatId, int startIndex, int numMessages);

    /**
     * Gets info about a user. Note that in order to save changes you must manually
     * call <code>insertUser()</code>
     * 
     * @param userId The id of the user
     * @return The info about the user
     */
    UserInfo getUser(UUID userId);

    /**
     * Append some messages to a chat history
     * 
     * @param chatId   The id of the chat
     * @param messages The list of messages
     */
    void insertMessages(UUID chatId, Message[] messages);

    /**
     * Insert or update a chat
     * 
     * @param chat The chat info
     */
    void insertChat(Chat chat);

    /**
     * Adds users to a chat's info and flush without adding the chat to the users'
     * info
     * 
     * @param chat  The chat to be added to
     * @param users The users to be added
     */
    void addUsersToChat(UUID chat, UUID[] users);

    /**
     * Insert or update a user
     * 
     * @param user The user info
     */
    void insertUser(UserInfo user);

    /**
     * This will add some chats to the user's info but not add the user to the
     * chats' info.
     * 
     * @param user  The user to add the chats to
     * @param chats The chats the user should be added to
     */
    void addUserToChats(UUID user, UUID[] chats);

    /**
     * This is used to clear the cache, removing unused data from memory. It should
     * be called periodically, though it
     * is a noop in BasicDatabase.
     */
    void garbageCollection();

    /**
     * Write any pending changes if they haven't already been written. In
     * BasicDatabase, this is a noop. In theory, having parts of the database
     * written in bursts could have performance gains, but some history could be
     * loss in crashes or shutdowns
     */
    public void flush();
}
