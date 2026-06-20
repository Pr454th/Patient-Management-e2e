package com.example.llamaAI.rag;

import com.example.llamaAI.rag.records.TavilyRequestPayload;
import com.example.llamaAI.rag.records.TavilyResponsePayload;
import org.apache.james.mime4j.dom.Header;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebSearchDocumentRetriever implements DocumentRetriever {
    private String TAVILY_API_KEY;

    private String TAVILY_BASE_URL;
    @Value("${tavily.result_size}")
    private int resultSize;
    private final int DEFAULT_RESULT_LIMIT;
    private final RestClient restClient;

    public WebSearchDocumentRetriever(@Value("${tavily.api.key}") String TAVILY_API_KEY,
                                      @Value("${tavily.url}") String TAVILY_BASE_URL,
                                      RestClient.Builder clientBuilder){
        this.TAVILY_API_KEY=TAVILY_API_KEY;
        this.TAVILY_BASE_URL=TAVILY_BASE_URL;
        this.DEFAULT_RESULT_LIMIT=5;
        this.restClient=clientBuilder
                .baseUrl(this.TAVILY_BASE_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer "+this.TAVILY_API_KEY)
                .build();
    }

    @Override
    public List<Document> retrieve(Query query) {
        String q=query.text();

        TavilyResponsePayload responsePayload=this.restClient.post()
                .body(new TavilyRequestPayload(q, "advanced", resultSize))
                .retrieve()
                .body(TavilyResponsePayload.class);

        if(responsePayload==null || CollectionUtils.isEmpty(responsePayload.results())) return List.of();

        List<Document> documents=responsePayload.results()
                .stream()
                .map(hit->{
                    return Document.builder()
                            .text(hit.content())
                            .metadata("title", hit.title())
                            .metadata("url", hit.url())
                            .score(hit.score())
                            .build();
                }).collect(Collectors.toList());
        return documents;
    }
}
