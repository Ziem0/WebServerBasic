package com.webServer.questbook.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webServer.questbook.helpers.MimeTypeResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public class Static implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();  // uri jest zwiazane z HTTP!
        String path = "." + uri.getPath();

        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(path);        //odnosi sie do target -> classes

        if (url == null) {
            send404(httpExchange);
        } else {
            sendFile(httpExchange, url);
        }
    }

    private void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 not found";
        httpExchange.sendResponseHeaders(404, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendFile(HttpExchange httpExchange, URL url) throws IOException {
        File file = new File(url.getFile());

        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();

        httpExchange.getResponseHeaders().set("Content-Type", mime);    // css image js etc   -> html can be created as response by using JtwigTemplate
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count = 0;
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        os.close();
    }
}