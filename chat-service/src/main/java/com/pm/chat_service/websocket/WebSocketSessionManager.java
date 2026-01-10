package com.pm.chat_service.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {
    Map<String, WebSocketSession> sessions=new ConcurrentHashMap<>();

    public void addSession(String chatId, WebSocketSession session){
        sessions.put(chatId, session);
    }

    public void removeSession(String chatId){
        sessions.remove(chatId);
    }

    public WebSocketSession getSession(String chatId){
        return sessions.get(chatId);
    }
}
