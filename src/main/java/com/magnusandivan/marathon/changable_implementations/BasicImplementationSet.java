package com.magnusandivan.marathon.changable_implementations;

import java.io.IOException;

import com.magnusandivan.marathon.api.ConnectionHandlerSingleton;
import com.magnusandivan.marathon.api.Database;
import com.magnusandivan.marathon.api.ImplementationSet;

public class BasicImplementationSet implements ImplementationSet {
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
