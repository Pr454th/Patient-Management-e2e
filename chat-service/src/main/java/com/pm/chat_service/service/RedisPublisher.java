package com.pm.chat_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.chat_service.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisPublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void publish(String chatId, String message) throws JsonProcessingException {
        ChatMessage chatMessage=mapper.readValue(message, ChatMessage.class);
        log.info("[ PUBLISH ]: {} {}", String.format("chat-%s", chatId), chatMessage.toString());
        redisTemplate.convertAndSend("chat-"+ chatId, message);
    }
}
