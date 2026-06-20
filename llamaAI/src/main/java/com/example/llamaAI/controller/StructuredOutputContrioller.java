package com.example.llamaAI.controller;

import com.example.llamaAI.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class StructuredOutputContrioller {
    @Autowired
    @Qualifier("ollamaChatClient2")
    private ChatClient chatClient;

    @GetMapping(path = "/chat-bean")
    public ResponseEntity<CountryCities> chat(@RequestParam("message") String message){

        CountryCities content=this.chatClient
                .prompt()
                .user(message)
                .call()
                .entity(CountryCities.class);
        return ResponseEntity.ok(content);
    }


    @GetMapping(path = "/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam("message") String message){

        List<String> content=this.chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(content);
    }

    @GetMapping(path = "/chat-map")
    public ResponseEntity<Map<String, Object>> chatMap(@RequestParam("message") String message){

        Map<String, Object> content=this.chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new MapOutputConverter());
        return ResponseEntity.ok(content);
    }


    @GetMapping(path = "/chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam("message") String message){

        List<CountryCities> content=this.chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(content);
    }
}
