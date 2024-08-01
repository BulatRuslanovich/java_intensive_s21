package edu.school21.service.impl;

import edu.school21.dao.UserDAO;
import edu.school21.dao.impl.UserDAOImpl;
import edu.school21.entity.User;
import edu.school21.excaptions.AlreadyAuthenticatedException;
import edu.school21.service.UserService;

import java.sql.Connection;
import java.util.Objects;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(Connection connection) {
        this.userDAO = new UserDAOImpl(connection);
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = userDAO.findByLogin(login);

        if (user.getStatus()) {
            throw new AlreadyAuthenticatedException("User already authenticated");
        }

        user.setStatus(Objects.equals(password, user.getPassword()));
        userDAO.update(user);

        return user.getStatus();
    }
}
