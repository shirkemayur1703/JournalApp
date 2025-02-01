package com.mayur.journalApp.service;

import com.mayur.journalApp.entity.User;
import com.mayur.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private  static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        }
        catch(Exception e){
            log.error("New User Saved");
            log.warn("New User Saved");
            log.info("New User Saved");
            log.debug("New User Saved");
            log.trace("New User Saved");
        }

    }
    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
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