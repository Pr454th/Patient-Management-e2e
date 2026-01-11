package com.pm.chat_service.service.mapper;

import com.pm.chat_service.dto.ChatCreateRequestDTO;
import com.pm.chat_service.model.Chat;
import com.pm.chat_service.model.ChatParticipant;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChatMapper {
    public Chat toModel(ChatCreateRequestDTO requestDTO){
        return Chat.builder()
                .chatName(requestDTO.getChatName())
                .type(requestDTO.getParticipants().size()>2?"GROUP":"DIRECT")
                .participants(requestDTO.getParticipants())
                .build();
    }

    public ChatParticipant toParticipantModel(String chatId, String userId){
        return ChatParticipant.builder()
                .chatId(UUID.fromString(chatId))
                .user_id(userId)
                .build();
    }

}
