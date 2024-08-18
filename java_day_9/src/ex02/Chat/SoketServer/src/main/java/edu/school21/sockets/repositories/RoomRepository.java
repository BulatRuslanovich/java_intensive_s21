package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Long, Chatroom> {
    Optional<Chatroom> findByTitle(String title);
}
