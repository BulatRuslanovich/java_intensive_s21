package edu.school21.chat.dao.impl;

import edu.school21.chat.dao.UserDAO;
import edu.school21.chat.entity.User;
import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.utils.ConnectionManager;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Getter
    private static final UserDAOImpl INSTANCE = new UserDAOImpl();

    private UserDAOImpl() {}

    private static final String FIND_ALL =
            "SELECT user_id,\n" +
            "       login,\n" +
            "       password\n" +
            "FROM chat.user\n" +
            "OFFSET ?\n" +
            "LIMIT ?";


    @Override
    public List<User> findAll(int page, int size) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            statement.setInt(1, page * size);
            statement.setInt(2, size);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            return users;
        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getLong("user_id"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .build();
    }
}
