package com.crk.journalApp.repository;

import com.crk.journalApp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
