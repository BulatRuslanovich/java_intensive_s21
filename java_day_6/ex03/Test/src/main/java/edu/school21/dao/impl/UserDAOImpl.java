package edu.school21.dao.impl;

import edu.school21.dao.UserDAO;
import edu.school21.entity.User;
import edu.school21.excaptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final static String UPDATE =
            "UPDATE app_user SET \n" +
            "login = ?, " +
            "password = ?, " +
            "status = ? " +
            "WHERE user_id = ?";
    private final static String FIND_BY_LOGIN = "SELECT * FROM app_user WHERE login = ?";


    private final Connection connection;

    @Override
    public User findByLogin(String login) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_LOGIN);
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                user = User.of(rs.getLong("user_id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getBoolean("status"));
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getStatus());
            statement.setLong(4, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
