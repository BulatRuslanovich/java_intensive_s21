package edu.school21.sockets.repositories.impl;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.mapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository("usersRepository")
public class UsersRepositoryImpl implements UsersRepository {
    private final JdbcTemplate template;
    private final UserMapper userMapper;

    public UsersRepositoryImpl(DataSource source, UserMapper userMapper) {
        this.template = new JdbcTemplate(source);
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM day09.app_user WHERE user_id = ?";
        return template.query(sql, userMapper, id).stream()
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM day09.app_user";
        return template.query(sql, userMapper);
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO day09.app_user (username, password) VALUES (?, ?)";
        template.update(sql, entity.getUsername(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE day09.app_user SET username = ?, password = ? WHERE user_id = ?";
        template.update(sql, entity.getUsername(), entity.getPassword(), entity.getUserId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM day09.app_user WHERE user_id = ?";
        template.update(sql, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM day09.app_user WHERE username = ?";
        return template.query(sql, userMapper, username).stream()
                .findFirst();
    }
}
