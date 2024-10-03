package com.magnusandivan.marathon.api;

import java.io.IOException;

import com.magnusandivan.marathon.Database;
import com.magnusandivan.marathon.changable_implementations.BasicConnectionHandlerSingleton;
import com.magnusandivan.marathon.changable_implementations.BasicDatabase;

public class ImplementationSet {
    public Database newDatabase() {
        try {
            return new BasicDatabase("data");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConnectionHandlerSingleton newWebsocketSingleton() {
        return new BasicConnectionHandlerSingleton();
    }
}
