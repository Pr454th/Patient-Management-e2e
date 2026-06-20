package com.example.llamaAI.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Component
public class RandomDataLoader {
    @Autowired
    private VectorStore vectorStore;

    @PostConstruct
    public void loadSentencesIntoVectorStore(){
        List<String> sentences= List.of(
                "Employees may work remotely two days per week.",

                "Spring AI supports retrieval augmented generation workflows.",

                "The Chennai office provides free shuttle service for employees.",

                "Vector databases enable semantic similarity search.",

                "Annual bonuses are processed during March every year.",

                "Ollama allows developers to run language models locally.",

                "Credit card payments must be completed before the due date.",

                "PostgreSQL with pgvector supports embedding storage.",

                "The cafeteria closes at 8 PM on Fridays.",

                "JWT tokens are commonly used for secure authentication.",

                "Gold ETFs track fluctuations in domestic gold prices.",

                "Docker containers simplify application deployment.",

                "VPN access is mandatory while working remotely.",

                "Redis improves performance through in-memory caching.",

                "Employees should reset passwords every 90 days.",

                "Kubernetes automates scaling and container orchestration.",

                "RAG systems retrieve relevant context before generating responses.",

                "Home loan approval depends on salary verification.",

                "Microservices improve scalability in distributed systems.",

                "Embeddings convert text into high-dimensional vectors.",

                "Performance reviews are conducted twice every year.",

                "MongoDB Atlas provides managed vector search capabilities.",

                "UPI transactions are usually processed instantly.",

                "Transformers changed the field of natural language processing.",

                "Production deployments require manager approval.",

                "Mutual funds are subject to market risks.",

                "Fine-tuning customizes model behavior using training data.",

                "Cloud computing reduces infrastructure maintenance costs.",

                "Insurance premiums vary depending on risk assessment.",

                "Tokenization splits text into smaller meaningful units."
        );

        List<Document> documents=sentences.stream().map(Document::new).collect(Collectors.toList());

        vectorStore.add(documents);
    }
}
