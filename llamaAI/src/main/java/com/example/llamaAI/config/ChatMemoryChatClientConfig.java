package com.example.llamaAI.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {

    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository){
//        Max messages limits the chat history
        return MessageWindowChatMemory.builder().maxMessages(10)
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .build();
    }

    @Bean("chatMemoryChatClient")
    public ChatClient ollamaChatClient(OllamaChatModel chatModel, ChatMemory chatMemory, RetrievalAugmentationAdvisor retrievalAugmentationAdvisor){
        ChatClient.Builder chatClientBuilder=ChatClient
                .builder(chatModel);

        ChatOptions chatOptions=ChatOptions.builder()
                .model("tinyllama")
                .build();

        Advisor memoryAdvisor=MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor simpleLoggerAdvisor=new SimpleLoggerAdvisor();
        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(memoryAdvisor, simpleLoggerAdvisor, retrievalAugmentationAdvisor))
                .build();
    }

    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore){
        return RetrievalAugmentationAdvisor
                .builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
                .build();
    }
}
