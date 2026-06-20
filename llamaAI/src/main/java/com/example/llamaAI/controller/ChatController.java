package com.example.llamaAI.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api")
public class ChatController {

    @Autowired
    @Qualifier("ollamaChatClient")
    private ChatClient chatClient;

    @GetMapping(path = "/chat")
    public String chat(@RequestParam String message){
        String content=this.chatClient
                .prompt()
                .user(message)
                .call().content();
        return content;
    }
}
