package com.webServer.questbook.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;

public class Cookie implements HttpHandler {

    int counter = 0;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        counter++;
        String response = "Page was visited: " + counter + " times!";
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");   //web sprawdza czy user posiada cookie
        HttpCookie cookie;
        boolean isNewSession;

        if (cookieStr != null) { // Cookie already exists
            cookie = HttpCookie.parse(cookieStr).get(0);
            isNewSession = false;
        } else { // Create a new cookie
            cookie = new HttpCookie("sessionId", String.valueOf(counter)); // This isn't a good way to create sessionId. Find out better!
            isNewSession = true;
            httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());  // jesli user nie mial ciastka (requestHeaders) tworzy je
        }

        response += "\n isNewSession: " + isNewSession;
        response += "\n session id: " + cookie.getName();      // key='sessionId' value=counter

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}