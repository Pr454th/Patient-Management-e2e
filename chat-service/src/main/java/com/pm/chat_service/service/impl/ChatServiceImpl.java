package com.pm.chat_service.service.impl;

import com.pm.chat_service.dto.ChatCreateRequestDTO;
import com.pm.chat_service.dto.ChatListResponseDTO;
import com.pm.chat_service.model.Chat;
import com.pm.chat_service.model.Participant;
import com.pm.chat_service.repository.ChatParticipantRepository;
import com.pm.chat_service.repository.ChatRepository;
import com.pm.chat_service.service.ChatService;
import com.pm.chat_service.service.mapper.ChatMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
                    Chat chat=repository.findById(participantInfo.getChatId()).orElse(null);
                    Participant otherParticipant=new Participant();
                    Map<String, String> participantMap=chat.getParticipants();
                    participantMap.entrySet().stream().forEach((participant)->{
                        if(!participant.getKey().equalsIgnoreCase(userId)){
                            otherParticipant.setName(participant.getValue());
                        }
                    });
                    if(chat!=null){
                        res.add(
                                ChatListResponseDTO.builder()
                                        .chatId(participantInfo.getChatId().toString())
                                        .chatName(chat.getType().equalsIgnoreCase("GROUP")?chat.getChatName():otherParticipant.getName())
                                        .build()
                        );
                    }
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

            chat.getParticipants().entrySet().forEach(
                    (participant) -> {
                        log.info(participant.getKey());
                        participantRepository.save(mapper.toParticipantModel(chat.getChatId().toString(), participant.getKey()));
                    }
            );
        }
        catch(Exception e){
            log.info("[ Exception while creating chat ]: {}", e.getMessage());
        }
        return "Chat created successfully!";
    }
}
