package edu.school21.spring.service.services.impl;

import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UserRepository;
import edu.school21.spring.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("userServiceImpl")
public class UserServiceImpl implements UserService {
    private UserRepository usersRepository;

    @Autowired
    public void UsersRepository(@Qualifier("jdbcTemplateRepository") UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String uuid = String.valueOf(UUID.randomUUID());
        usersRepository.save(new User(null, email, uuid));
        return uuid;
    }
}
