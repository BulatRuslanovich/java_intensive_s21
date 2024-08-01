package edu.school21.dao;

import edu.school21.entity.User;

public interface UserDAO {
    User findByLogin(String login);
    void update(User user);
}
