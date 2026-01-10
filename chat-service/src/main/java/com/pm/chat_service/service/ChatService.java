package com.pm.chat_service.service;

import com.pm.chat_service.dto.ChatCreateRequestDTO;
import com.pm.chat_service.dto.ChatListResponseDTO;

import java.util.List;

public interface ChatService {
    public String createChat(ChatCreateRequestDTO requestDTO);

    public List<String> getParticipants(String chatId);

    public List<ChatListResponseDTO> getChats(String userId);
}
