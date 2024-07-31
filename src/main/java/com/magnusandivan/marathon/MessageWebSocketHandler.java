package com.magnusandivan.marathon;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// This contains all of the code for managing the messages websocket
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        // Print the received message
        System.out.println(String.format("Received message: %s", message.getPayload()));
        // Send back a message "abc"
        session.sendMessage(new TextMessage("Message received on server and sent back to client"));
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession sesison) {

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}