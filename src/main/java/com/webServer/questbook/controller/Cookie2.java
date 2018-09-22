package com.webServer.questbook.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;

public class Cookie2 implements HttpHandler {

    int counter = 0;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        counter++;
        String response = "This site has been visited " + counter + " times!";
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        boolean isNewSession;

        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
            isNewSession = false;
        } else {
            cookie = new HttpCookie("sessionID", String.valueOf(counter));
            isNewSession = true;
            httpExchange.getResponseHeaders().add("Set-Cookie", String.valueOf(cookie));
        }

        response += "\nisNewSession: " + isNewSession;
        response += "\nsessionID: " + cookie.getValue();

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
