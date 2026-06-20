package com.example.llamaAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping(path = "/api")
public class ChatMemoryController {

    @Autowired
    @Qualifier("chatMemoryChatClient")
    private ChatClient chatClient;

    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMemory(@RequestHeader("username") String username, @RequestParam("message") String message){
        System.out.println(chatClient.toString());
        return ResponseEntity.ok(this.chatClient.prompt()
                        .advisors(
                                advisorSpec -> advisorSpec.param(CONVERSATION_ID, username)
                        )
                .user(message).call().content());
    }

//    RED
}
