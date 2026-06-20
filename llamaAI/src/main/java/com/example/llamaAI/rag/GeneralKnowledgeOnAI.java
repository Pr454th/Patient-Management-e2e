package com.example.llamaAI.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class GeneralKnowledgeOnAI {

    @Autowired
    private VectorStore vectorStore;

    @Value("classpath:/rag_data/rag_friendly_document_nomic_embed_text.pdf")
    Resource data;

    @PostConstruct
    void loadPDF(){

        TokenTextSplitter textSplitter=TokenTextSplitter.builder()
                .withChunkSize(100)
                .withMaxNumChunks(200)
                .build();
        TikaDocumentReader documentReader=new TikaDocumentReader(data);

        List<Document> documentList=textSplitter.apply(documentReader.get());

        vectorStore.add(documentList);
    }
}
