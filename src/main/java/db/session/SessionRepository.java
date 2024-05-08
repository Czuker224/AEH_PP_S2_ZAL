package db.session;

import db.dbConnection;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SessionRepository {
    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String INSERT_SESSION_SQL = "INSERT INTO SESSION (token, inputDate) VALUES (?, ?)";

    public void addSession(String token) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SESSION_SQL)) {

            preparedStatement.setString(1, token);
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }
}
