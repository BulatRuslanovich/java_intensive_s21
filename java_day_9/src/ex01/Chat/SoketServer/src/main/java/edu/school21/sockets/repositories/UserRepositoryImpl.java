package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.user (" +
                "id SERIAL PRIMARY KEY," +
                "username VARCHAR(40) NOT NULL UNIQUE," +
                "password VARCHAR(100) NOT NULL);");
    }


    @Override
    public Optional<User> findById(Long id) {
        final String FIND_BY_ID = "SELECT * FROM server.user WHERE id = ?";
        return jdbcTemplate.query(FIND_BY_ID,
                new Object[]{id},
                new int[]{Types.BIGINT},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }

    @Override
    public List<User> findAll() {
        final String FIND_ALL = "SELECT * FROM server.user";
        return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    @Transactional
    public void save(User entity) {
        final String SAVE_QUERY = "INSERT INTO server.user (username, password) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(SAVE_QUERY, entity.getUsername(), entity.getPassword());

        if (rowsAffected == 0) {
            log.error("User wasn't saved: {}", entity);
        }
    }

    @Override
    @Transactional
    public void update(User entity) {
        final String UPDATE_QUERY = "UPDATE server.user SET username = ?, password = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(UPDATE_QUERY, entity.getUsername(),
                entity.getPassword(), entity.getId());

        if (rowsAffected == 0) {
            log.error("User wasn't updated: {}", entity);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final String DELETE_QUERY = "DELETE FROM server.user WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(DELETE_QUERY, id);

        if (rowsAffected == 0) {
            log.error("User not found with id: {}", id);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        final String FIND_BY_USERNAME = "SELECT * FROM server.user WHERE username = ?";
        User user = jdbcTemplate.query(FIND_BY_USERNAME,
                new Object[]{username},
                new int[]{Types.VARCHAR},
                new BeanPropertyRowMapper<>(User.class)).stream()
                .findAny()
                .orElse(null);
        return Optional.ofNullable(user);
    }
}
