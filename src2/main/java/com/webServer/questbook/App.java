package com.webServer.questbook;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {
        ConnectBook dao = ConnectBook.getConnectBook();
        EntryList entryList = dao.getEntries();

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);

        httpServer.createContext("/", new Guestbook(entryList));
        httpServer.createContext("/static", new Static());
        httpServer.createContext("/sign", new NewEntry(entryList));

        httpServer.setExecutor(null);
        httpServer.start();
    }
}