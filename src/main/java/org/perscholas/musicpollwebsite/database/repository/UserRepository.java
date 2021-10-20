package org.perscholas.musicpollwebsite.database.repository;

import org.perscholas.musicpollwebsite.database.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
