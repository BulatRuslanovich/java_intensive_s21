package edu.school21.chat.dao.impl;

import edu.school21.chat.dao.MessageDAO;
import edu.school21.chat.entity.CharRoom;
import edu.school21.chat.entity.Message;
import edu.school21.chat.entity.User;
import edu.school21.chat.utils.ConnectionManager;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class MessageDAOImpl implements MessageDAO {
    @Getter
    private static final MessageDAOImpl INSTANCE = new MessageDAOImpl();
    private static final String SELECT_MESSAGE_BY_ID =
            "SELECT message_id,\n" +
            "       c.chatroom_id,\n" +
            "       c.name,\n" +
            "       u.user_id,\n" +
            "       u.login,\n" +
            "       u.password,\n" +
            "        text,\n" +
            "        date_time\n" +
            "       FROM chat.message\n" +
            "JOIN chat.user u USING (user_id)\n" +
            "JOIN chat.chatroom c USING(chatroom_id)\n" +
            "WHERE message_id = ?";

    private MessageDAOImpl() {}

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_MESSAGE_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long messageId = resultSet.getLong("message_id");
                long authorId = resultSet.getLong("user_id");
                long chatroomId = resultSet.getLong("chatroom_id");
                String text = resultSet.getString("text");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                String chatName = resultSet.getString("name");
                String password = resultSet.getString("password");
                String login = resultSet.getString("login");

                User user = User.builder().userId(authorId)
                        .login(login)
                        .password(password)
                        .build();

                CharRoom charRoom = CharRoom.builder()
                        .chatRoomId(chatroomId)
                        .name(chatName)
                        .build();

                Message message = Message.builder()
                        .messageId(messageId)
                        .author(user)
                        .room(charRoom)
                        .text(text)
                        .dateTime(dateTime.toLocalDateTime())
                        .build();

                return Optional.of(message);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
