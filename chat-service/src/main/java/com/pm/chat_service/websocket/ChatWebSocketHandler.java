package com.pm.chat_service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pm.chat_service.service.ChatService;
import com.pm.chat_service.service.RedisPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final RedisPublisher redisPublisher;
    private final WebSocketSessionManager sessionManager;

    @Autowired
    private ChatService chatService;

    public ChatWebSocketHandler(RedisPublisher redisPublisher,
                                WebSocketSessionManager sessionManager) {
        this.redisPublisher = redisPublisher;
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("SESSION : {}", session.toString());
        String userId = getUserId(session);
        log.info("user Id: {}", userId);
        sessionManager.addSession(userId, session);
    }

    public String getUserId(WebSocketSession session){
        return session.getUri().getQuery().split("=")[1];
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        log.info("[ HANDLE ]: {} {}", session, message.getPayload());
        redisPublisher.publish(getUserId(session), message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = session.getUri().getQuery().split("=")[1];
        sessionManager.removeSession(userId);
    }

    public void sendMessage(String sender, String chatId, String message) throws IOException {
        chatService.getParticipants(chatId).stream()
            .forEach(
                    (participantId)->{
                        if(!participantId.equalsIgnoreCase(sender)){
                            WebSocketSession session=sessionManager.getSession(participantId);
                            WebSocketMessage socketMessage=new TextMessage("{ message:"+message+", chatId:"+chatId+"}");
                            log.info("[ Sending message to the connected session ]: {} {}", message, session);
                            try {
                                session.sendMessage(socketMessage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );
    }
}

