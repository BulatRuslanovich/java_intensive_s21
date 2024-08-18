package edu.school21.spring.service.repositories.mapper;

import edu.school21.spring.service.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("user_id"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
