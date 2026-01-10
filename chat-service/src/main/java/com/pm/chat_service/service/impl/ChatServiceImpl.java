package com.pm.chat_service.service.impl;

import com.pm.chat_service.dto.ChatCreateRequestDTO;
import com.pm.chat_service.dto.ChatListResponseDTO;
import com.pm.chat_service.model.Chat;
import com.pm.chat_service.repository.ChatParticipantRepository;
import com.pm.chat_service.repository.ChatRepository;
import com.pm.chat_service.service.ChatService;
import com.pm.chat_service.service.mapper.ChatMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper mapper;

    @Autowired
    private ChatRepository repository;

    @Autowired
    private ChatParticipantRepository participantRepository;

    @Override
    public List<String> getParticipants(String chatId) {
        List<String> res=new ArrayList<>();
        participantRepository.findAllByChatId(UUID.fromString(chatId))
                .forEach((participant)->{
                    res.add(participant.getUser_id());
                });
        log.info("[ PARTICIPANTS ]: {}", res.toString());
        return res;
    }

    @Override
    public List<ChatListResponseDTO> getChats(String userId) {
        List<ChatListResponseDTO> res=new ArrayList<>();
        participantRepository.findAllByUserId(userId)
                .forEach((participantInfo)->{
                    res.add(
                            ChatListResponseDTO.builder()
                                    .chatId(participantInfo.getChatId().toString())
                                    .chatName(repository.getChatName(participantInfo.getChatId()))
                                    .build()
                    );
                });
        return res;
    }

    @Transactional
    @Override
    public String createChat(ChatCreateRequestDTO requestDTO) {
        try {
            log.info(requestDTO.toString());
            Chat chat = mapper.toModel(requestDTO);
            repository.save(chat);
            log.info(chat.toString());

            Arrays.stream(chat.getParticipants()).forEach(
                    (participant) -> {
                        log.info(participant);
                        participantRepository.save(mapper.toParticipantModel(chat.getChatId().toString(), participant));
                    }
            );
        }
        catch(Exception e){
            log.info("[ Exception while creating chat ]: {}", e.getMessage());
        }
        return "Chat created successfully!";
    }
}
