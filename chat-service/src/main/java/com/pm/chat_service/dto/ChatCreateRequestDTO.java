package com.pm.chat_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatCreateRequestDTO {
    private String chatName;
    private String type="DIRECT";
    private String[] participants;
}
