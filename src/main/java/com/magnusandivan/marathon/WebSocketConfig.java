package com.magnusandivan.marathon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.magnusandivan.marathon.api.ConnectionHandlerSingleton;
import com.magnusandivan.marathon.changable_implementations.BasicConnectionHandlerSingleton;

// This file will configure which urls point to websocket handlers
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    // This forces it to springboot to load this class as it is needed to initialize
    // WebSocketHandler
    @Bean
    public ConnectionHandlerSingleton connectionHandlerSingleton() {
        return new BasicConnectionHandlerSingleton();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("Registering websocket listener");
        registry.addHandler(new WebSocketHandler(MarathonApplication.ImplementationSet.newWebsocketSingleton()), "/ws");
    }
}
