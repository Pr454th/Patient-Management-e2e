package com.example.llamaAI.rag.records;

import java.util.List;

public record TavilyResponsePayload(List<Hit> results) {
    public record Hit(String title, String url, String content, Double score){}
}
