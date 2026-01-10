package com.pm.chat_service.repository;

import com.pm.chat_service.model.Chat;
import com.pm.chat_service.model.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, UUID> {
    public List<ChatParticipant> findAllByChatId(UUID chatId);

    @Query(value = "SELECT * FROM CHAT_PARTICIPANT WHERE USER_ID=:user_id", nativeQuery = true)
    public List<ChatParticipant> findAllByUserId(String user_id);
}
