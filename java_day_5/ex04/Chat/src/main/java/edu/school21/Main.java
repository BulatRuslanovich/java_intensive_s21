package edu.school21;

import edu.school21.chat.dao.UserDAO;
import edu.school21.chat.dao.impl.UserDAOImpl;
import edu.school21.chat.entity.User;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserDAO userDAO = UserDAOImpl.getINSTANCE();

        List<User> users = userDAO.findAll(1, 3);
        System.out.println(Collections.unmodifiableList(users));
    }
}