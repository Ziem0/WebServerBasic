package com.webServer.questbook.model;

import com.webServer.questbook.dao.EntryDao;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Entry {
    private int id;
    private final String name;
    private final String message;
    private final LocalDateTime dateTime;

    public Entry(int id, String name, String message, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Entry(String name, String message) {
        this.name = name;
        this.message = message;
        this.dateTime = LocalDateTime.now();
        EntryDao dao = EntryDao.getDao();
        dao.saveNewEntry(this);
    }
}
