package com.example.llamaAI.config;

import com.example.llamaAI.advisors.TokenUsageAuditAdvisor;
import com.example.llamaAI.rag.WebSearchDocumentRetriever;
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
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class WebSearchRAGChatClientConfig {
    @Bean("webSearchChatClient")
    public ChatClient chatClient(OllamaChatModel chatModel,
                                 ChatMemory chatMemory,
                                 RestClient.Builder restClientBuilder,
                                 WebSearchDocumentRetriever webSearchDocumentRetriever){
        ChatClient.Builder builder=ChatClient.builder(chatModel);

        Advisor loggerAdvisor=new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor=new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor=MessageChatMemoryAdvisor.builder(chatMemory).build();

        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor=
                RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(webSearchDocumentRetriever)
                        .build();

        return builder
                .defaultAdvisors(List.of(loggerAdvisor, tokenUsageAdvisor, memoryAdvisor, retrievalAugmentationAdvisor))
                .build();
    }
}
