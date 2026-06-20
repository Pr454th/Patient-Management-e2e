package com.example.llamaAI.rag.records;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TavilyRequestPayload(String query, String searchDepth, int maxResults) {
}
