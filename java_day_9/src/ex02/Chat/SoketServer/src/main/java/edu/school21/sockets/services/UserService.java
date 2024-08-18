package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean signIn(String username, String password);
    void signUp(User user);
    void createMessage(String message, Long authorId, Long roomId);
    List<Message> getAllMessageByRoomId(Long roomId);
    Optional<Message> findLastRoom(Long authorId);
}
