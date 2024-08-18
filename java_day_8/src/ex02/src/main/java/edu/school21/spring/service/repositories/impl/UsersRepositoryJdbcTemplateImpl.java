package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.mapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("jdbcTemplateRepository")
public class UsersRepositoryJdbcTemplateImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        String SQL = "SELECT * FROM day08_2.app_user WHERE user_id = ?";
        List<User> users = jdbcTemplate.query(SQL, new UserMapper(), id);
        return users.stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        String SQL = "SELECT * FROM day08_2.app_user";
        return jdbcTemplate.query(SQL, new UserMapper());
    }

    @Override
    public void save(User entity) {
        String SQL = "INSERT INTO day08_2.app_user(email, password) VALUES (?, ?)";
        jdbcTemplate.update(SQL, entity.getEmail(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        String SQL = "UPDATE day08_2.app_user SET email = ? WHERE user_id = ?";
        jdbcTemplate.update(SQL, entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String SQL = "DELETE FROM day08_2.app_user WHERE user_id = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String SQL = "SELECT * FROM day08_2.app_user WHERE email = ?";
        List<User> users = jdbcTemplate.query(SQL, new UserMapper(), email);
        return users.stream().findFirst();
    }
}
