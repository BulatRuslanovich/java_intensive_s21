package edu.school21.chat.dao;

import edu.school21.chat.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll(int page, int size);
}
