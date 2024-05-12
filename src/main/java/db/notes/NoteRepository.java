package db.notes;

import db.dbConnection;
import db.session.SessionRepository;

import java.sql.*;
import java.util.logging.Logger;

public class NoteRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String INSERT_NOTE_SQL = "INSERT INTO NOTES (inputDate, responsibleUser, state, type, description, planedDeadline) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_NOTE_SQL = "UPDATE NOTES SET responsibleUser = ?, state = ?, type = ?, description = ?, planedDedline = ? WHERE id = ?";
    private static final String DELETE_NOTE_SQL = "DELETE NOTES  WHERE id = ?";

    public int addNote(Date inputDate, Integer responsibleUser, String state, String type, String description, Date planedDeadline) {
        ResultSet generatedKeys = null;
        int generatedKey = 0;
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NOTE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDate(1, inputDate);
            preparedStatement.setInt(2, responsibleUser);
            preparedStatement.setString(3, state);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, description);
            preparedStatement.setDate(6, planedDeadline);
            preparedStatement.executeUpdate();

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        } finally {
            if (generatedKeys != null) try {
                generatedKeys.close();
            } catch (SQLException logOrIgnore) {
            }
        }
        return generatedKey;
    }

    public void updateNote(Integer id, Date inputDate, Integer responsibleUser, String state, String type, String description, Date planedDeadline) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE_SQL)) {

            preparedStatement.setInt(1, responsibleUser);
            preparedStatement.setString(2, state);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, description);
            preparedStatement.setDate(5, planedDeadline);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    public void deleteNote(Integer id) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE_SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

}
