package com.movies.tfi.repository;

import com.movies.tfi.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Long> {
    Optional<User> findByUsernameOrEmail(String username, String email );
}
