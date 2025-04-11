package com.magnusandivan.marathon.api;

public interface ImplementationSet {
    public Database newDatabase();

    public ConnectionHandlerSingleton newWebsocketSingleton();
}
