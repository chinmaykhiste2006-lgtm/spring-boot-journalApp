package com.crk.journalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crk.journalApp.model.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}