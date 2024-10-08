package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Long, Message> {
    List<Message> findAllByRoom(Long roomId);
    Optional<Message> findLastRoomByAuthor(Long authorId);
}
