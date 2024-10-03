package com.magnusandivan.marathon.changable_implementations;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import com.magnusandivan.marathon.api.ConnectionHandler;
import com.magnusandivan.marathon.api.ConnectionHandlerSingleton;
import com.magnusandivan.marathon.api.Database;

public class BasicConnectionHandler extends ConnectionHandler {
    public UUID uuid;

    public BasicConnectionHandler(ConnectionHandlerSingleton singleton, WebSocketSession session) {
        super(singleton, session);
        Database database = getSingleton().getDatabase();
        for (Message m : database.getChat(database.globalChatId()).getRecentMessages()) {
            if (m == null) {
                return;
            }
            sendPayload(m.value);
        }
        uuid = UUID.randomUUID();
    }

    @Override
    public void handlePayload(String payload) {
        List<ConnectionHandler> handlers = getSingleton().getAllHandlers();
        for (int i = 0; i < handlers.size(); i++) {
            ConnectionHandler handler = handlers.get(i);
            if (handler != this) {
                if (!handler.sendPayload(payload)) {
                    // If the send fails, then the handler will be removed and we can subtract one
                    // from the index
                    i--;
                }
            }
        }
        Database database = getSingleton().getDatabase();
        database.insertMessages(database.globalChatId(), new Message[] { new Message(payload, Instant.now(), uuid) });
        System.out.println("Received message:\n\t" + payload);
    }

    @Override
    public void onDisconnected(CloseStatus status) {
        System.out.println("Connection closed with status: " + status.toString());
    }

}
