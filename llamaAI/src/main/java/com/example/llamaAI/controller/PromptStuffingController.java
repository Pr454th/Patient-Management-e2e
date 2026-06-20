package com.example.llamaAI.controller;

import com.example.llamaAI.advisors.TokenUsageAuditAdvisor;
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
public class PromptStuffingController {
    @Autowired
    @Qualifier("ollamaChatClient")
    private ChatClient chatClient;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    @GetMapping(path = "/prompt-stuffing")
    public String promptStuffing(@RequestParam("message") String message){

        String content=this.chatClient
                .prompt()
                .advisors(new TokenUsageAuditAdvisor())
                .system(systemPromptTemplate)
                .user(message
                )
                .call().content();
        return content;
    }
}
