package db;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static final String DATABASE_URL = "src/main/resources/db/database";
    private static final Logger logger = Logger.getLogger(dbConnection.class.getName());

    public static Connection createDatabaseConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_URL);
        } catch (SQLException e) {
            logger.severe("Database connection failed: " + e);
        }
        if (connection == null) {
            logger.severe("Database connection = null");
        }
        return connection;
    }
}
