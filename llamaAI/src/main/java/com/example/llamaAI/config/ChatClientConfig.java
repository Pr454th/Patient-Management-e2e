package com.example.llamaAI.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel){
        ChatClient.Builder chatClientBuilder=ChatClient.builder(chatModel);

        ChatOptions chatOptions=ChatOptions.builder()
                .temperature(0.3)
                .maxTokens(200)
                .stopSequences(List.of("END"))
                .build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem("""
                       You are a very good Data Structure and Algorithms expert and include 'Happy New Year' text at the end of every response
                        """)
                .build();
    }

    @Bean
    public ChatClient ollamaChatClient2(OllamaChatModel chatModel){
        ChatClient.Builder chatClientBuilder=ChatClient
                .builder(chatModel);

        ChatOptions chatOptions=ChatOptions.builder()
                .model("tinyllama")
                .build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
