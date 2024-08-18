package edu.school21.sockets.repositories.mapper;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component("userMapper")
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .userId(rs.getLong("user_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .build();
    }
}
