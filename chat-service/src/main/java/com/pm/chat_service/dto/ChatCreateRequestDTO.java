package com.pm.chat_service.dto;

import com.pm.chat_service.model.Participant;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ChatCreateRequestDTO {
    private String chatName;
    private String type;
    private Map<String, String> participants;
}
