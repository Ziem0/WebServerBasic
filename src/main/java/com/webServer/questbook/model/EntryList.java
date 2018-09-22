package com.webServer.questbook.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class EntryList {

    private List<Entry> entries;

    public EntryList() {
        this.entries = new LinkedList<>();
    }

    public Entry getEntry(Entry entry) {
        if (!entries.contains(entry)) {
            entries.add(entry);
        }
        return entry;
    }
}

