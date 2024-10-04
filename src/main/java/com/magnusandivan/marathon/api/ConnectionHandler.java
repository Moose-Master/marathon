package com.magnusandivan.marathon.api;

import java.io.IOException;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.magnusandivan.marathon.UserInfo;

public abstract class ConnectionHandler {
    private ConnectionHandlerSingleton singleton;
    private WebSocketSession websocketSession;
    private UserInfo user;
    private boolean disconnected;

    /**
     * Handle an incoming text message
     * 
     * @param payload
     */
    public abstract void handlePayload(String payload);

    /**
     * Called when the handler has been disconnected for some reason, though not
     * always immediately. When this is called, the handler will still be in
     * lists(such as the active users for a chat) but the session will be closed.
     * Immediately after this is called, it is removed from all lists.
     * 
     * @param status The status it closed with
     */
    public abstract void onDisconnected(CloseStatus status);

    public ConnectionHandler(ConnectionHandlerSingleton singleton, WebSocketSession session) {
        this.singleton = singleton;
        this.websocketSession = session;
    }

    public boolean getDisconnected() {
        return disconnected;
    }

    /**
     * Get the associated singleton
     * 
     * @return The singleton
     */
    public ConnectionHandlerSingleton getSingleton() {
        return this.singleton;
    }

    /**
     * Returns the associated websocket session for lower level operations, you can
     * probably ignore this
     * 
     * @return The websocket session
     */
    public WebSocketSession getWebsocketSession() {
        return this.websocketSession;
    }

    /**
     * Returns the user this is associated with. Unless <code>setUser</code> was
     * already called, this will be null.
     * 
     * @return The user
     */
    public UserInfo getUser() {
        return user;
    }

    /**
     * Sets the user this should be associated with.
     * 
     * @param user the user
     */
    public void setUser(UserInfo user) {
        this.user = user;
    }

    void setDisconnected() {
        this.disconnected = true;
    }

    /**
     * Attempts to send a message
     * 
     * @param payload The message to send
     * @return Whether this was successful. If it wasn't, the handler will have been
     *         cleaned up already
     */
    public boolean sendPayload(String payload) {
        if (disconnected) {
            return false;
        } else {
            try {
                websocketSession.sendMessage(new TextMessage(payload));
                return true;
            } catch (Exception e) {
                // If an error occurs, close the session immediately
                getSingleton().closeSession(websocketSession, CloseStatus.NO_STATUS_CODE);
                return false;
            }
        }
    }
}
