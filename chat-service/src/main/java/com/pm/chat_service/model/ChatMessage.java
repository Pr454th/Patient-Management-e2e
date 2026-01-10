package com.pm.chat_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Entity
public class ChatMessage {
    @Id
    private String chatId;
    private String sender;
    private String message;
}
