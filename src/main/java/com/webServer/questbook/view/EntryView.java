package com.webServer.questbook.view;

import com.webServer.questbook.model.Entry;
import com.webServer.questbook.model.EntryList;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class EntryView {

    public String getEntriesView(EntryList entryList) {
        List<Entry> entries = new LinkedList<>(entryList.getEntries());
        StringBuilder input = new StringBuilder();

        for (Entry entry : entries) {
            String name = entry.getName();
            String message = entry.getMessage();
            String dateTime = entry.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);

            input.append("<tr><td>" +
                    "<br>name: " + name +
                    "<br>message: " + message +
                    "<br>date: " + dateTime +
                    "</td></tr><br>");
        }
        return input.toString();
    }
}
