package com.pm.chat_service.controller;

import com.pm.chat_service.dto.ChatCreateRequestDTO;
import com.pm.chat_service.dto.ChatListResponseDTO;
import com.pm.chat_service.dto.MessageRequestDTO;
import com.pm.chat_service.service.ChatService;
import com.pm.chat_service.service.RedisPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/chat")
public class ChatController {

    @Autowired
    private ChatService service;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createChat(@RequestBody ChatCreateRequestDTO requestDTO){
        String response=service.createChat(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/user/{id}/chats")
    public ResponseEntity<List<ChatListResponseDTO>> getUserChats(@PathVariable("id") String userId){
        List<ChatListResponseDTO> responseDTOS=
                service.getChats(userId);
        return ResponseEntity.ok(responseDTOS);
    }

    @PostMapping
    public ResponseEntity<String> pubish(@RequestBody MessageRequestDTO requestDTO){
//        publisher.publish(requestDTO.getMessage());
        return ResponseEntity.ok("Message sent!");
    }
}
