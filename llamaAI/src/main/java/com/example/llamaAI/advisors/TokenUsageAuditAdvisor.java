package com.example.llamaAI.advisors;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenUsageAuditAdvisor implements CallAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse response=callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse=response.chatResponse();

        if(chatResponse.getMetadata()!=null){
            Usage usage=chatResponse.getMetadata().getUsage();
            if(usage!=null){
                logger.info("Token Usage: {}", usage.toString());
            }
        }

        return response;
    }

    @Override
    public String getName() {
        return "TokenUsageAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
