package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Long, User> {
    Optional<User> findByUsername(String username);
}
