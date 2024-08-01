package edu.school21.chat.dao.impl;

import edu.school21.chat.dao.MessageDAO;
import edu.school21.chat.entity.CharRoom;
import edu.school21.chat.entity.Message;
import edu.school21.chat.entity.User;
import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.utils.ConnectionManager;
import lombok.Getter;

import java.sql.*;
import java.time.ZoneOffset;
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

    private static final String SAVE_MESSAGE =
            "INSERT INTO chat.message (user_id, chatroom_id, text, date_time)\n" +
            "VALUES (?, ?, ?, ?)";

    private MessageDAOImpl() {}

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(SELECT_MESSAGE_BY_ID)) {

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

    @Override
    public Long save(Message message) {
        try (Connection connection = ConnectionManager.open();
        PreparedStatement statement = connection.prepareStatement(SAVE_MESSAGE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, message.getAuthor().getUserId());
            statement.setLong(2, message.getRoom().getChatRoomId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, Timestamp.from(message.getDateTime().toInstant(ZoneOffset.ofHours(3))));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            return resultSet.getLong("message_id");
        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }

}
