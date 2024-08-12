package com.magnusandivan.marathon;
import java.time.Instant;
import java.util.UUID;

public class Message {
    String value;
    Instant timestamp;
    UUID senderId;

    public Message() {

    }
    public Message(String value, Instant timestamp, UUID senderId) {
        this.value = value;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", timestamp.toString(), senderId.toString(), value);
    }
}
