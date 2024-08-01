package edu.school21.chat.dao;

import edu.school21.chat.entity.Message;

import java.util.Optional;

public interface MessageDAO {
    Optional<Message> findById(Long id);
}
