package com.crk.journalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crk.journalApp.model.ConfigJournalAppEntity;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, String> {

}
