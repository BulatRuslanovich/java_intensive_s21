package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MessageRepositoryImpl implements MessageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.message (" +
                "id SERIAL PRIMARY KEY," +
                "message TEXT NOT NULL," +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public Optional<Message> findById(Long id) {
        final String FIND_BY_ID_QUERY = "SELECT * FROM server.message WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new MessageRowMapper(), id));
        } catch (Exception e) {
            log.error("Error finding message by ID", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        final String FIND_ALL_QUERY = "SELECT * FROM server.message";
        return jdbcTemplate.query(FIND_ALL_QUERY, new MessageRowMapper());
    }

    @Override
    @Transactional
    public void save(Message entity) {
        final String SAVE_QUERY = "INSERT INTO server.message (message) VALUES (?)";
        int rowsAffected = jdbcTemplate.update(SAVE_QUERY, entity.getMessageText());

        if (rowsAffected == 0) {
            log.error("Message wasn't saved: {}", entity);
        }
    }

    @Override
    @Transactional
    public void update(Message entity) {
        final String UPDATE_QUERY = "UPDATE server.message SET message = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(UPDATE_QUERY, entity.getMessageText(), entity.getId());

        if (rowsAffected == 0) {
            log.error("Message wasn't updated: {}", entity);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final String DELETE_QUERY = "DELETE FROM server.message WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(DELETE_QUERY, id);

        if (rowsAffected == 0) {
            log.error("Message wasn't deleted with ID: {}", id);
        }
    }

    private static class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int i) throws SQLException {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setMessageText(rs.getString("message"));
            message.setTime(rs.getTimestamp("time").toLocalDateTime());
            return message;
        }
    }
}

