package com.magnusandivan.marathon.api;

import java.io.IOException;

import com.magnusandivan.marathon.Database;
import com.magnusandivan.marathon.changable_implementations.BasicConnectionHandlerSingleton;
import com.magnusandivan.marathon.changable_implementations.BasicDatabase;

public interface ImplementationSet {
    public Database newDatabase();

    public ConnectionHandlerSingleton newWebsocketSingleton();
}
