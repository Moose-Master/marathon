package com.magnusandivan.marathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// This contains all of the code for managing the messages websocket
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {
    // We want each handler to be treated differently
    public class IndividualHandler {
        // To be used for finding the instance that sent or should receive a message
        public static HashMap<String, IndividualHandler> Map = new HashMap<>();
        // To be used for easy iterating to send message to all handlers
        public static List<IndividualHandler> All = new ArrayList<>();
        
        WebSocketSession session;
        UserInfo user;

        public IndividualHandler(WebSocketSession session) {
            this.session = session;
            //System.out.println("Connection established");
            All.add(this);
            this.user = null;
        }
        public void handleMessage(TextMessage message) throws IOException {
            String payload = message.getPayload();
            // Print the received message
            // System.out.println(String.format("Received message: %s", payload));
            // Send back a message "abc"
            for(IndividualHandler handler : All) {
                if (handler != this) {
                    if (handler.session.isOpen()) {
                        try {
                            handler.session.sendMessage(new TextMessage(payload));
                        } catch (IOException e) {
                            // This is just a reminder to deal with this later
                            // throw e;
                        }
                    }
                }
            }
            session.sendMessage(new TextMessage("Message successfully sent"));
        }
        public void connectionClosed(CloseStatus status) {
            IndividualHandler.Map.remove(session.getId());
            All.remove(this);
            if (user != null) {
                user.activeUser = null;
                for (UUID chatId : user.chatIds) {
                    MarathonApplication.Database.getChat(chatId).activeUsers.remove(this);
                }
            }
            // System.out.println("Closed connection: " + status.toString());
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        IndividualHandler handler = IndividualHandler.Map.get(session.getId());
        if(handler == null) {
            handler = new IndividualHandler(session);
            IndividualHandler.Map.put(session.getId(), handler);
        }
        handler.handleMessage(message);
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        if (!IndividualHandler.Map.containsKey(session.getId())) {
            IndividualHandler.Map.put(session.getId(), new IndividualHandler(session));
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        IndividualHandler handler = IndividualHandler.Map.get(session.getId());
        if(handler != null) {
            handler.connectionClosed(status);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}