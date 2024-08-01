package edu.school21;

import edu.school21.chat.dao.MessageDAO;
import edu.school21.chat.dao.impl.MessageDAOImpl;
import edu.school21.chat.entity.CharRoom;
import edu.school21.chat.entity.Message;
import edu.school21.chat.entity.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws SQLException {
        MessageDAO messageDAO = MessageDAOImpl.getINSTANCE();

        User user = User.builder()
                .login("Ryan Gosling 5")
                .userId(5L)
                .password("4567")
                .createdRooms(new ArrayList<>())
                .socializeRooms(new ArrayList<>())
                .build();

        CharRoom charRoom = CharRoom.builder()
                .chatRoomId(5L)
                .name("art")
                .owner(user)
                .messages(new ArrayList<>())
                .build();

        Message message = Message.builder()
                .author(user)
                .room(charRoom)
                .text("lol")
                .dateTime(LocalDateTime.now())
                .build();

        System.out.println(messageDAO.save(message));
    }
}