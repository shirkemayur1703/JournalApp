package com.mayur.journalApp.service;

import com.mayur.journalApp.entity.JournalEntry;
import com.mayur.journalApp.entity.User;
import com.mayur.journalApp.repository.JournalEntryRepository;
import com.mayur.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public void deleteAll() {
        userRepository.deleteAll();
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
// controller->service->repository