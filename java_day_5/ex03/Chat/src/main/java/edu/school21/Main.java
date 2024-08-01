package edu.school21;

import edu.school21.chat.dao.MessageDAO;
import edu.school21.chat.dao.impl.MessageDAOImpl;
import edu.school21.chat.entity.Message;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        MessageDAO messageDAO = MessageDAOImpl.getINSTANCE();

        Optional<Message> optionalMessage = messageDAO.findById(4L);

        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setText("Popit");
            message.setDateTime(null);
            messageDAO.update(message);
            System.out.println(message);
        }
    }
}