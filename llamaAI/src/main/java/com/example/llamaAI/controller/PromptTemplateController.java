package com.example.llamaAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class PromptTemplateController {
    @Autowired
    @Qualifier("ollamaChatClient")
    private ChatClient chatClient;

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource promptTemplate;

    @GetMapping(path = "/email")
    public String chat(@RequestParam("customerName") String name,
                       @RequestParam("customerMessage") String message){

        String content=this.chatClient
                .prompt()
                .user(promptUserSpec ->
                        promptUserSpec.text(promptTemplate)
                                .param("name", name)
                                .param("message", message)
                )
                .call().content();
        return content;
    }
}
