package edu.school21.sockets.services;

import edu.school21.sockets.exceptions.UserAlreadyExistsException;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           MessageRepository messageRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    @Override
    @Transactional
    public void signUp(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            log.error("Sign-up failed: Username already exists - {}", user.getUsername());
            throw new UserAlreadyExistsException("Username: " + user.getUsername() + " already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User signed up successfully: {}", user.getUsername());
    }

    @Override
    public boolean signIn(String username, String password) {
        Optional<User> opt = userRepository.findByUsername(username);

        if (!opt.isPresent()) {
            log.error("Sign-in failed: User not found - {}", username);
            return false;
        }

        boolean isAuthenticated = passwordEncoder.matches(password, opt.get().getPassword());
        if (!isAuthenticated) {
            log.error("Sign-in failed: Incorrect password for username - {}", username);
        }
        return isAuthenticated;
    }

    @Override
    @Transactional
    public void createMessage(String message) {
        messageRepository.save(Message.builder().messageText(message).build());
        log.info("Message created successfully: {}", message);
    }
}
