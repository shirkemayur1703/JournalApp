package com.mayur.journalApp.service;

import com.mayur.journalApp.entity.JournalEntry;
import com.mayur.journalApp.entity.User;
import com.mayur.journalApp.repository.JournalEntryRepository;
import com.mayur.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    public void saveEntry(JournalEntry journalEntry, String userName){

        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteById(String userName,ObjectId id){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
    public void deleteAll() {
        journalEntryRepository.deleteAll();
    }

}
// controller->service->repository