package com.epic_echoes.epic_echoes.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .addInterceptors(httpSessionHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler())
                .setAllowedOrigins("*") // Change to the origin you expect
                .withSockJS();
    }

    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request,
                                           org.springframework.http.server.ServerHttpResponse response,
                                           org.springframework.web.socket.WebSocketHandler wsHandler,
                                           Map<String, Object> attributes) throws Exception {
                if (request instanceof ServletServerHttpRequest) {
                    HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
                    String token = servletRequest.getHeader("Authorization");

                    if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7);
                        attributes.put("jwt", token);
                        System.out.println("JWT Token: " + token);
                    } else {
                        System.out.println("Invalid or missing Authorization header");
                    }
                }
                return true;
            }


            @Override
            public void afterHandshake(ServerHttpRequest request,
                                       org.springframework.http.server.ServerHttpResponse response,
                                       org.springframework.web.socket.WebSocketHandler wsHandler,
                                       Exception exception) {
            }
        };
    }
}
