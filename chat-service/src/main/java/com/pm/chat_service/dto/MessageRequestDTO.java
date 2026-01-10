package com.pm.chat_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageRequestDTO {
    private String message;
}
