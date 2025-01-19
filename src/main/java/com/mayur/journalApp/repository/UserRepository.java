package com.mayur.journalApp.repository;

import com.mayur.journalApp.entity.JournalEntry;
import com.mayur.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
