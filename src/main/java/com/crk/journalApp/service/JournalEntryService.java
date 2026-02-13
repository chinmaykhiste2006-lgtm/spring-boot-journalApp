package com.crk.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.crk.journalApp.model.JournalEntry;
import com.crk.journalApp.model.User;
import com.crk.journalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUsername(userName);
            if (!user.getSentimentAnalysis() && journalEntry.getSentiment() != null) {
                logger.error("sentiment analysis is not enabled");
                throw new RuntimeException("sentiment analysis is not enabled");
            }
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveExistingUser(user);
        } catch (Exception e) {
            logger.info("error occured");
            throw new RuntimeException("an error occured while saving entry", e);
        }

    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public void updateEntry() {

    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(String id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id, String userName) {

        try {
            User user = userService.findByUsername(userName);
            user.getStarredEntries().removeIf(i -> i.equals(id));
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));

            if (removed) {
                userService.saveExistingUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occured while deleting the entry");
        }
    }

    public void deleteById(String id) {
        journalEntryRepository.deleteById(id);
    }
}