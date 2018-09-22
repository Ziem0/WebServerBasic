package com.webServer.questbook;

import com.sun.net.httpserver.HttpServer;
import com.webServer.questbook.controller.*;
import com.webServer.questbook.dao.EntryDao;
import com.webServer.questbook.model.EntryList;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        EntryList entryList = new EntryList();
        EntryDao dao = EntryDao.getDao();
        dao.createEntriesFromDB(entryList);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/static", new Static());
        server.createContext("/", new Home(entryList));
        server.createContext("/add", new AddRecord(entryList));
        server.createContext("/cookie", new Cookie());
        server.createContext("/cookie2", new Cookie2());
        server.setExecutor(null);
        server.start();
    }
}