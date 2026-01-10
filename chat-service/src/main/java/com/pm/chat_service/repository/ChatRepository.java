package com.pm.chat_service.repository;

import com.pm.chat_service.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query(value = "SELECT CHAT_NAME FROM CHAT WHERE CHAT_ID=:chatId", nativeQuery = true)
    public String getChatName(UUID chatId);
}
