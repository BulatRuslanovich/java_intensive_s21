package edu.school21.chat.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";

    private ConnectionManager() {}

    public static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(DB_URL), PropertiesUtil.get(DB_USERNAME), PropertiesUtil.get(DB_PASSWORD));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
