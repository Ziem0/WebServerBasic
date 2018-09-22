package com.webServer.questbook.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webServer.questbook.model.EntryList;
import com.webServer.questbook.view.EntryView;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class Home implements HttpHandler {

    private final EntryList entryList;

    public Home(EntryList entryList) {
        this.entryList = entryList;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        EntryView entryView = new EntryView();
        String entries = entryView.getEntriesView(entryList);

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/index.html");
        JtwigModel model = JtwigModel.newModel();
        model.with("entries", entries);

        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

// /static -> wszystkie pliki znajdujace sie po /static/..
// rel w html -> sciezka web url
// response tylko w home and add(dla get) budowane za pomocą Jtwig
// static nie ma response  + okreslam content-type
// add and post -> input stream reader + parsowanie do map +
// response = "<html><head><meta http-equiv=\"refresh\" content=\"0; url=/\" /></head><html>";