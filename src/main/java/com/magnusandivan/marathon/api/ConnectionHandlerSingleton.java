package com.magnusandivan.marathon.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.magnusandivan.marathon.MarathonApplication;

public abstract class ConnectionHandlerSingleton {
    private Database database;
    // To be used for finding the instance that sent or should receive a message
    private HashMap<String, ConnectionHandler> map = new HashMap<>();
    // To be used for easy iterating to send message to all handlers
    private List<ConnectionHandler> all = new ArrayList<>();

    /**
     * Allows you to create and use custom ConnectionHandler's
     * 
     * @param session the session to be used
     * @return A new connection handler to be associated with this session
     */
    public abstract ConnectionHandler newHandler(WebSocketSession session);

    /**
     * Use the database used by the main app
     */
    public ConnectionHandlerSingleton() {
        this(MarathonApplication.Database);
    }

    /**
     * Specify a database to use
     * 
     * @param database The database to use with this singleton
     */
    public ConnectionHandlerSingleton(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    /**
     * Get the handler associated with the session
     * 
     * @param session The session
     * @return A handler
     */
    public ConnectionHandler getHandler(WebSocketSession session) {
        return map.get(session.getId());
    }

    /**
     * An of all connections when this is called. These may become disconnected over
     * time.
     * 
     * @return an array of all connections
     */
    public ConnectionHandler[] getAllHandlers() {
        return all.toArray(new ConnectionHandler[0]);
    }

    /**
     * Closes the session and its associated handler
     * 
     * @param session the session to be closed
     * @param status  the status to close it with if it hasn't already been closed,
     *                use CloseStatus.NO_STATUS_CODE if you don't know what status
     *                to use
     */
    public void closeSession(WebSocketSession session, CloseStatus status) {
        ConnectionHandler handler = getHandler(session);
        if (handler != null) {

            try {
                handler.getWebsocketSession().close(status);
            } catch (IOException e) {
            }
            handler.onDisconnected(status);
            map.remove(session.getId());
            all.remove(handler);
            if (handler.getUser() != null) {
                handler.getUser().activeUser = null;
                for (UUID chatId : handler.getUser().chatIds) {
                    MarathonApplication.Database.getChat(chatId).activeUsers.remove(handler);
                }
            }
        }
    }

    /**
     * <i><b>IGNORE</b></i>
     * 
     * @param session
     * @param message
     * @throws InterruptedException
     * @throws IOException
     */
    public void _handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        ConnectionHandler handler = map.get(session.getId());
        if (handler == null) {
            handler = setupNewHandler(session);
            map.put(session.getId(), handler);
        }
        handler.handlePayload(message.getPayload());
    }

    /**
     * <i><b>IGNORE</b></i>
     * 
     * @param session
     * @param message
     * @throws InterruptedException
     * @throws IOException
     */
    public void _afterConnectionEstablished(WebSocketSession session) {
        if (!map.containsKey(session.getId())) {
            map.put(session.getId(), setupNewHandler(session));
        }
    }

    /**
     * <i><b>IGNORE</b></i>
     * 
     * @param session
     * @param message
     * @throws InterruptedException
     * @throws IOException
     */
    public void _afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        ConnectionHandler handler = map.get(session.getId());
        if (handler != null && !handler.getDisconnected()) {
            handler.setDisconnected();
            handler.onDisconnected(status);
        }
    }

    private ConnectionHandler setupNewHandler(WebSocketSession session) {
        ConnectionHandler handler = newHandler(session);
        this.map.put(session.getId(), handler);
        this.all.add(handler);
        return handler;
    }
}
