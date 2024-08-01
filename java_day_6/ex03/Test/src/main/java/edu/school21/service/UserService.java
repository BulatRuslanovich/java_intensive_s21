package edu.school21.service;

import edu.school21.entity.User;

public interface UserService {
    boolean authenticate(String login, String password);
}
