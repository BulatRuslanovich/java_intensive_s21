package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM day08.app_user WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {

            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Long userId = rs.getLong("user_id");
                    String email = rs.getString("email");
                    return Optional.of(new User(userId, email));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM day08.app_user";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stm = connection.prepareStatement(query);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("user_id");
                String email = rs.getString("email");
                users.add(new User(id, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User entity) {
        String query = "INSERT INTO day08.app_user(email) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stm.setString(1, entity.getEmail());
            stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    entity.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String query = "UPDATE day08.app_user SET email = ? WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {

            stm.setString(1, entity.getEmail());
            stm.setLong(2, entity.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM day08.app_user WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {

            stm.setLong(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM day08.app_user WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {

            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("user_id");
                    String emailUser = rs.getString("email");
                    return Optional.of(new User(id, emailUser));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
