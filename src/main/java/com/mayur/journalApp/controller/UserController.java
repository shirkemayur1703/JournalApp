package com.mayur.journalApp.controller;

import com.mayur.journalApp.entity.JournalEntry;
import com.mayur.journalApp.entity.User;
import com.mayur.journalApp.service.JournalEntryService;
import com.mayur.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers= userService.getAll();
        if(allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> createEntry(@RequestBody User user){
        try{
            userService.saveUser(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<User> updateJournalEntryById(@RequestBody User user, @PathVariable String userName){
        User userInDb=userService.findByUserName(userName);
        if(userInDb!=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
            return new ResponseEntity<>(userInDb,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
