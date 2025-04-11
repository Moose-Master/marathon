package com.magnusandivan.marathon;

import java.io.IOException;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.magnusandivan.marathon.api.ConnectionHandlerSingleton;

// This contains all of the code for managing the messages websocket
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    public ConnectionHandlerSingleton singleton;

    public WebSocketHandler(ConnectionHandlerSingleton singleton) {
        System.out.println("Websocket handler created");
        this.singleton = singleton;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        singleton._handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        singleton._afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        singleton._afterConnectionClosed(session, status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}