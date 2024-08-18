package edu.school21.service;

import edu.school21.dao.UserDao;
import edu.school21.entity.User;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void update(User user) {
        userDao.update(user);
    }
}
