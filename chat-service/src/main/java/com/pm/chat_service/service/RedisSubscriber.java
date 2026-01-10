package com.pm.chat_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.chat_service.websocket.ChatWebSocketHandler;
import com.pm.chat_service.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RedisSubscriber implements MessageListener {
    @Autowired
    private ChatWebSocketHandler handler;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ChatMessage chatMessage= null;
        try {
            chatMessage = mapper.readValue(message.getBody(), ChatMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("[ SUBSCRIBER ]: {}: {}", chatMessage, message.getChannel());
        try {
            handler.sendMessage(chatMessage.getSender(), chatMessage.getChatId(), chatMessage.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
