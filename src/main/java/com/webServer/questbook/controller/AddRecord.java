package com.webServer.questbook.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webServer.questbook.model.Entry;
import com.webServer.questbook.model.EntryList;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class AddRecord implements HttpHandler {

    private final EntryList entryList;

    public AddRecord(EntryList entryList) {
        this.entryList = entryList;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String response = "";

        if (method.equalsIgnoreCase("post")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String inputs = br.readLine();
            Map formedInputs = formInputs(inputs);
            String name = (String) formedInputs.get("name");
            String message = (String) formedInputs.get("message");
            entryList.getEntry(new Entry(name, message));
            response = "<html><head><meta http-equiv=\"refresh\" content=\"0; url=/\" /></head><html>";

        } else if (method.equalsIgnoreCase("get")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/add.html");
            JtwigModel model = JtwigModel.newModel();
            response = template.render(model);
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> formInputs(String inputs) {
        Map<String, String> formedInputs = new HashMap<>();
        String[] pairs = inputs.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1]);
            formedInputs.put(keyValue[0], value);
        }
        return formedInputs;
    }
}
