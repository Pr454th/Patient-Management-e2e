package com.example.llamaAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping(path = "/api/rag")
public class RAGController {
    @Autowired
    @Qualifier("chatMemoryChatClient")
    private ChatClient chatClient;

    @Autowired
    @Qualifier("webSearchChatClient")
    private ChatClient webSearchChatClient;

    @Autowired
    private VectorStore vectorStore;

    @Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
    Resource promptTemplate;

    @Value("classpath:/promptTemplates/systemPromptGKDataTemplate.st")
    Resource promptTemplate1;

    @GetMapping(path = "/random/chat")
    ResponseEntity<String> randomChat(@RequestHeader("username") String username, @RequestParam("message") String message){
        SearchRequest searchRequest=SearchRequest.builder()
                .query(message)
                .topK(3)
                .similarityThreshold(0.5)
                .build();
        List<Document> similarDocs=vectorStore.similaritySearch(searchRequest);

        String similarContext=similarDocs.stream().map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        String response=chatClient.prompt().system(promptSystemSpec -> promptSystemSpec.text(promptTemplate).param("documents", similarContext))
                .advisors(a->a.param(CONVERSATION_ID, username))
                .user(message)
                .call().content();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/random/chat1")
    ResponseEntity<String> webSearchChat(@RequestHeader("username") String username, @RequestParam("message") String message){
//        SearchRequest searchRequest=SearchRequest.builder()
//                .query(message)
//                .topK(3)
//                .similarityThreshold(0.5)
//                .build();
//        List<Document> similarDocs=vectorStore.similaritySearch(searchRequest);
//
//        String similarContext=similarDocs.stream().map(Document::getText)
//                .collect(Collectors.joining(System.lineSeparator()));

        String response=chatClient.prompt()
//                .system(promptSystemSpec -> promptSystemSpec.text(promptTemplate1).param("documents", similarContext))
                .advisors(a->a.param(CONVERSATION_ID, username))
                .user(message)
                .call().content();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/random/ws/chat")
    ResponseEntity<String> randomGKChat(@RequestHeader("username") String username, @RequestParam("message") String message){
        String response=webSearchChatClient.prompt()
                .advisors(a->a.param(CONVERSATION_ID, username))
                .user(message)
                .call().content();

        return ResponseEntity.ok(response);
    }
}
