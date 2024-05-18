package db.notes;

import db.dbConnection;
import db.session.SessionRepository;
import notes.Note;

import java.sql.*;
import java.util.logging.Logger;

public class NoteRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String INSERT_NOTE_SQL = "INSERT INTO NOTES (inputDate, responsibleUser, state, type, description, planedDeadline) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_NOTE_SQL = "UPDATE NOTES SET responsibleUser = ?, state = ?, type = ?, description = ?, planedDedline = ? WHERE id = ?";
    private static final String DELETE_NOTE_SQL = "DELETE NOTES  WHERE id = ?";
    private static final String SELECT_NOTE_SQL = "SELECT * FROM NOTES WHERE id = ?";

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

    public Note getNote(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        Note note = null;
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOTE_SQL)) {

            preparedStatement.setInt(1, id);

            System.out.println("Executing SQL Query: " + SELECT_NOTE_SQL.replace("?", String.valueOf(id)));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer responsibleUser = resultSet.getInt("responsibleUser");
                String state = resultSet.getString("state");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                Date plannedDeadline = resultSet.getDate("planedDeadline");

                note = new Note(id,responsibleUser,state,type,description,plannedDeadline);
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return note;
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
