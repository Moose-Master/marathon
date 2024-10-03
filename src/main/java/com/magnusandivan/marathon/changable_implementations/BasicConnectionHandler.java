package com.magnusandivan.marathon.changable_implementations;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import com.magnusandivan.marathon.api.ConnectionHandler;
import com.magnusandivan.marathon.api.ConnectionHandlerSingleton;

public class BasicConnectionHandler extends ConnectionHandler {

    public BasicConnectionHandler(ConnectionHandlerSingleton singleton, WebSocketSession session) {
        super(singleton, session);
    }

    @Override
    public void handlePayload(String payload) {
        for (ConnectionHandler handler : getSingleton().getAllHandlers()) {
            if (handler != this) {
                handler.sendPayload(payload);
            }
        }
        System.out.println("Received message:\n\t" + payload);
    }

    @Override
    public void onDisconnected(CloseStatus status) {
        System.out.println("Connection closed with status: " + status.toString());
    }

}
