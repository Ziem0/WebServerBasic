package com.webServer.questbook.dao;

import com.webServer.questbook.model.Entry;
import com.webServer.questbook.model.EntryList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EntryDao {

    private static EntryDao dao = null;
    private static Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet result = null;

    private ArrayList<ArrayList<String>> results;

    private EntryDao() {
        connection = ConnectDB.getConnection();
    }

    public static EntryDao getDao() {
        if (dao == null) {
            synchronized (EntryDao.class) {
                if (dao == null) {
                    dao = new EntryDao();
                }
            }
        }
        return dao;
    }

    private void processQuery(String query, String[] stringSet) {           // should return results and be public
        handleQuery(query, stringSet);
    }

    private void handleQuery(String query, String[] stringSet) {
        try {
            statement = connection.prepareStatement(query);
            buildQuery(statement, stringSet);

            if (query.startsWith("select")) {
                result = statement.executeQuery();
                downloadResults();
            } else {
                statement.executeUpdate();
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close() throws SQLException {
        statement.close();
        if (result != null) {
            result.close();
        }
    }

    private void buildQuery(PreparedStatement statement, String[] stringsSet) throws SQLException {
        if (stringsSet != null) {
            for (int i = 1; i <= stringsSet.length; i++) {
                statement.setString(i,stringsSet[i-1]);
            }
        }
    }

    private void downloadResults() throws SQLException {
        results = new ArrayList<>();

        int columnCounter = result.getMetaData().getColumnCount();

        while (result.next()) {
            ArrayList<String> recordResult = new ArrayList<>();
            for (int column = 1; column <= columnCounter; column++) {
                recordResult.add(String.valueOf(result.getString(column)));
            }
            results.add(recordResult);
        }
    }

    ////////////////////////////////////// all handle type foo should be in ConnectDB

    public void createEntriesFromDB(EntryList entryList) {
        String query = "select * from entries order by id desc limit 5;";
        processQuery(query, null);

        for (ArrayList<String> entry : results) {
            Integer id = Integer.parseInt(entry.get(0));
            String name = entry.get(1);
            String message = entry.get(2);
            String dateTime = entry.get(3);
            LocalDateTime ldt = LocalDateTime.parse(dateTime);

            entryList.getEntry(new Entry(id, name, message, ldt));
        }
    }

    public void saveNewEntry(Entry entry) {
        String name = entry.getName();
        String message = entry.getMessage();
        String dateTime = entry.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME);

        String command = "insert into entries(name, message, date) values(?,?,?);";

        processQuery(command, new String[] {name, message, dateTime});
    }
}
