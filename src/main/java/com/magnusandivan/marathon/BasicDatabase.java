package com.magnusandivan.marathon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BasicDatabase implements Database {
    public static final int RecentlyKeptMessages = 100;
    class CacheItem<T> {
        public static final long MAX_SECONDS_UNUSED = 60;
        public T item;
        public Instant lastUsed;

        public static List<UUID> toRemove = new ArrayList<>();

        public static <T> void clearCache(HashMap<UUID, CacheItem<T>> map) {
            Instant now = Instant.now();
            for (Map.Entry<UUID, CacheItem<T>> a : map.entrySet()) {
                if (Duration.between(a.getValue().lastUsed, now).getSeconds() > CacheItem.MAX_SECONDS_UNUSED) {
                    toRemove.add(a.getKey());
                }
            }
            for (UUID uuid : toRemove) {
                map.remove(uuid);
            }
            toRemove.clear();
        }
    }

    Path directory;
    HashMap<UUID, CacheItem<UserInfo>> userCache = new HashMap<>();
    HashMap<UUID, CacheItem<Chat>> chatCache = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    public BasicDatabase(String dataPath) throws IOException {
        this.directory = Path.of(dataPath);
        if (Files.isDirectory(directory)) {

        } else {
            Files.createDirectory(directory);
        }
        Files.createDirectories(Path.of(dataPath, "chat-metadata"));
        Files.createDirectories(Path.of(dataPath, "chat-logs"));
        Files.createDirectories(Path.of(dataPath, "users"));
    }

    @Override
    public Chat getChat(UUID chatId) {
        {
            CacheItem<Chat> chat = chatCache.get(chatId);
            if (chat != null)
                return chat.item;
        }

        try {
            FileReader reader = new FileReader(directory.resolve("chat-metadata/", chatId.toString() + ".json").toFile());
            Chat chat = mapper.readValue(reader, Chat.class);
            chat.id = chatId;
            chat.activeUsers = new ArrayList<>();
            
            // All of this to load the recently sent messages
            chat.recentMessagesStart = 0;
            if (chat.currentMessageIndex < RecentlyKeptMessages) {
                chat.recentMessages = new Message[RecentlyKeptMessages];
                Message[] toCopy = getMessages(chatId, 0, chat.currentMessageIndex);
                for (int i = 0;i < toCopy.length;i++) {
                    chat.recentMessages[i] = toCopy[i];
                }
            } else {
                chat.recentMessages = getMessages(chatId, chat.currentMessageIndex - RecentlyKeptMessages, RecentlyKeptMessages);
            }
            return chat;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message[] getMessages(UUID chatId, int startIndex, int numMessages) {
        try {
            Message[] messages = new Message[numMessages];
            BufferedReader reader = new BufferedReader(new FileReader(directory.resolve("chat-logs/", chatId.toString() + ".json").toFile()));
            for (int i = 0;i < startIndex;i++) {
                reader.readLine();
            }
            for(int i = 0;i < numMessages;i++) {
                
                messages[i] = mapper.readValue(reader.readLine(), Message.class);
            }
            reader.close();
            return messages;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserInfo getUser(UUID userId) {
        {
            CacheItem<UserInfo> user = userCache.get(userId);
            if (user != null)
                return user.item;
        }

        try {
            FileReader reader = new FileReader(directory.resolve("users/", userId.toString() + ".json").toFile());
            UserInfo user = mapper.readValue(reader, UserInfo.class);
            user.id = userId;
            user.activeUser = null; // It would already be in cache if the user was active
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void insertMessages(UUID chatId, Message[] messages) {
        try {
            File file = directory.resolve("chat-logs/", chatId.toString() + ".json").toFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            for(Message msg : messages) {
                writer.write(mapper.writeValueAsString(msg));
                writer.write((int)'\n');
            }
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addUsersToChat(UUID chatId, UUID[] users) {
        Chat chat = getChat(chatId);
        for (UUID userId : users) {
            chat.userIds.add(userId);
            UserInfo user = getUser(userId);
            if (user.activeUser != null) chat.activeUsers.add(user.activeUser);
        }
        insertChat(chat);
    }
    @Override
    public void insertChat(Chat chat) {
        try {
            mapper.writeValue(directory.resolve("chat-metadata/", chat.id.toString() + ".json").toFile(), chat);
            File otherFile = directory.resolve("chat-logs/", chat.id.toString() + ".json").toFile();
            otherFile.createNewFile(); // Doesn't overwrite existing file(I think)
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void insertUser(UserInfo user) {
        try {
            mapper.writeValue(directory.resolve("users/", user.id.toString() + ".json").toFile(), user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addUserToChats(UUID userId, UUID[] chats) {
        UserInfo user = getUser(userId);
        for (UUID chat : chats) {
            user.chatIds.add(chat);
        }
        insertUser(user);
    }
    
    @Override
    public void garbageCollection() {
        CacheItem.clearCache(this.chatCache);
        // We want a special version to not remove from the cache users which are logged in but not active
        Instant now = Instant.now();
        for (Map.Entry<UUID, CacheItem<UserInfo>> a : userCache.entrySet()) {
            if (Duration.between(a.getValue().lastUsed, now).getSeconds() > CacheItem.MAX_SECONDS_UNUSED && a.getValue().item.activeUser == null) {
                CacheItem.toRemove.add(a.getKey());
            }
        }
        for (UUID uuid : CacheItem.toRemove) {
            userCache.remove(uuid);
        }
        CacheItem.toRemove.clear();
    }
    @Override
    public void flush() {
        // We won't flush anything because in this basic database everything is automatically written immediately to the output
    }
}
