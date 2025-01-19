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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);

        List<JournalEntry> userEntries= user.getJournalEntries();
        if(userEntries != null && !userEntries.isEmpty()){
            return new ResponseEntity<>(userEntries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try{

            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName,@PathVariable ObjectId journalId){
        journalEntryService.deleteById(userName,journalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id,@PathVariable String userName, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry=journalEntryService.findById(id).orElse(null);
        if(oldEntry!=null){
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent():oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteAllJournalEntries() {
        journalEntryService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
