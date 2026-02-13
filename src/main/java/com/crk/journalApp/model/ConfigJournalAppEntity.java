package com.crk.journalApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "config_journal_app")
@Data
@NoArgsConstructor
@Getter
@Setter
public class ConfigJournalAppEntity {

    @Id
    private String id;

    private String key;

    private String value;

}
