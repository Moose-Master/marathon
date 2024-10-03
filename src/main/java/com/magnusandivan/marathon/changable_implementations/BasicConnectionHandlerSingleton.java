package com.magnusandivan.marathon.changable_implementations;

import org.springframework.web.socket.WebSocketSession;

import com.magnusandivan.marathon.api.ConnectionHandler;
import com.magnusandivan.marathon.api.ConnectionHandlerSingleton;

public class BasicConnectionHandlerSingleton extends ConnectionHandlerSingleton {

    @Override
    public ConnectionHandler newHandler(WebSocketSession session) {
        return new BasicConnectionHandler(this, session);
    }

}
