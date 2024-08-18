package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class RoomRepositoryImpl implements RoomRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.rooms (\n" +
                "id serial primary key,\n" +
                "title varchar(40) not null unique, \n" +
                "owner_id bigint not null);");
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        String idQuery = "SELECT * FROM server.rooms WHERE id = ?";
        return jdbcTemplate.query(idQuery,
                new Object[]{id},
                new int[]{Types.BIGINT},
                new BeanPropertyRowMapper<>(Chatroom.class)).stream().findAny();
    }

    @Override
    public List<Chatroom> findAll() {
        String alQuery = "SELECT * FROM server.rooms";
        return jdbcTemplate.query(alQuery, new BeanPropertyRowMapper<>(Chatroom.class));
    }

    @Override
    public void save(Chatroom entity) {
        String inQuery = "INSERT INTO server.rooms (title, owner_id) VALUES (?, ?)";
        int i = jdbcTemplate.update(inQuery, entity.getTitle(), entity.getOwnerId());

        if (i == 0) {
            System.err.println("Room wasn't saved: " + entity);
        }
    }

    @Override
    public void update(Chatroom entity) {
        String upQuery = "UPDATE server.rooms SET title = ?, owner_id = ? WHERE id = ?";
        int i = jdbcTemplate.update(upQuery, entity.getTitle(),
                entity.getOwnerId(), entity.getId());

        if (i == 0) {
            System.err.println("Room wasn't updated: " + entity);
        }
    }

    @Override
    public void delete(Long id) {
        String dlQuery = "DELETE FROM server.rooms WHERE id = ?";
        int i = jdbcTemplate.update(dlQuery, id);

        if (i == 0) {
            System.err.println("Room not found with id: " + id);
        }
    }

    @Override
    public Optional<Chatroom> findByTitle(String title) {
        String usQuery = "SELECT * FROM server.rooms WHERE title = ?";

        return jdbcTemplate.query(usQuery,
                new Object[]{title},
                new int[]{Types.VARCHAR},
                new BeanPropertyRowMapper<>(Chatroom.class)).stream().findAny();
    }
}
