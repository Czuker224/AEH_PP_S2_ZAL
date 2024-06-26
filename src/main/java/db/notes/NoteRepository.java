package db.notes;

import db.dbConnection;
import db.session.SessionRepository;
import notes.Note;
import session.Session;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Klasa obsługująca operacje na bazie danych związane z Notatkami
 */
public class NoteRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());

    private static final String INSERT_NOTE_SQL = "INSERT INTO NOTES (inputDate, responsibleUser, state, type, description, planedDeadline,team) VALUES (?, ?, ?, ?, ?, ?,?)";
    private static final String UPDATE_NOTE_SQL = "UPDATE NOTES SET responsibleUser = ?, state = ?, type = ?, description = ?, planedDeadline = ?,team = ? WHERE id = ?";
    private static final String DELETE_NOTE_SQL = "DELETE NOTES  WHERE id = ?";
    private static final String SELECT_NOTE_SQL = "SELECT * FROM NOTES WHERE id = ?";

    private static final String SELECT_GET_ALL_USER_NOTES_SQL = "SELECT * FROM NOTES WHERE responsibleUser = ?";

    private static final String SELECT_NOTE_HISTORY_SQL = "SELECT * FROM NOTES_HISTORY WHERE id = ?";
    private static final String SELECT_NOTE_HISTORY_FOR_NOTEID_SQL = "SELECT * FROM NOTES_HISTORY WHERE noteId = ?";
    private static final String SELECT_NOTE_HISTORY_FOR_NOTEID_ONLYOPEN_SQL = "SELECT * FROM NOTES_HISTORY WHERE dateEnd is null and noteId = ? and userId = ?";
    private static final String INSERT_NOTE_HISTORY_SQL = "INSERT INTO NOTES_HISTORY (noteId, userId, dateStart) VALUES (?, ?, ?)";
    private static final String UPDATE_NOTE_HISTORY_SQL = "UPDATE NOTES_HISTORY SET dateEnd = ? WHERE Id = ?";

    /**
     * Dodaje notatkę do bazy danych z podanymi informacjami.
     *
     * @param inputDate         Data dodania notatki.
     * @param responsibleUser   Identyfikator użytkownika odpowiedzialnego za notatkę.
     * @param state             Stan notatki.
     * @param type              Typ notatki.
     * @param description       Opis notatki.
     * @param planedDeadline    Planowany termin wykonania notatki.
     * @param team              Identyfikator zespołu powiązanego z notatką.
     * @return Wygenerowany klucz dodanej notatki w bazie danych.
     */
    public int addNote(Date inputDate, Integer responsibleUser, String state, String type, String description, Date planedDeadline, Integer team) {
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
            preparedStatement.setInt(7, team);
            preparedStatement.executeUpdate();

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            }

            if (generatedKey != 0) {
                addNoteHistory(generatedKey,responsibleUser);
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

    /**
     * Pobiera obiekt notatki z bazy danych na podstawie podanego identyfikatora.
     *
     * @param id Identyfikator notatki do pobrania. Nie może być wartością null.
     * @return Obiekt notatki odpowiadający podanemu identyfikatorowi, lub null, jeśli taka notatka nie istnieje.
     * @throws IllegalArgumentException jeśli parametr id ma wartość null.
     */
    public Note getNote(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        Note note = null;
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOTE_SQL)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer responsibleUser = resultSet.getInt("responsibleUser");
                Integer team = resultSet.getInt("team");
                String state = resultSet.getString("state");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                Date plannedDeadline = resultSet.getDate("planedDeadline");

                note = new Note(id,responsibleUser,state,type,description,plannedDeadline,team);
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return note;
    }

    /**
     * Pobiera notatki powiązane z użytkownikiem.
     *
     * @param userId Identyfikator użytkownika, dla którego mają być pobrane notatki. Nie może być wartością null.
     * @return Lista obiektów notatek powiązanych z użytkownikiem.
     * @throws IllegalArgumentException Jeśli userId ma wartość null.
     */
    public List<notes.Note> getUserNotes(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        List<Note> notes = new ArrayList<>();
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GET_ALL_USER_NOTES_SQL)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer team = resultSet.getInt("team");
                String state = resultSet.getString("state");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                Date plannedDeadline = resultSet.getDate("planedDeadline");

                if(id != null){
                    notes.add(new Note(id,userId,state,type,description,plannedDeadline,team));
                }

            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return notes;
    }

    /**
     * Aktualizuje notatkę przy użyciu podanych parametrów.
     *
     * @param id Identyfikator notatki do zaktualizowania.
     * @param inputDate Data wprowadzenia dla notatki.
     * @param responsibleUser Użytkownik odpowiedzialny za notatkę.
     * @param state Stan notatki.
     * @param type Typ notatki.
     * @param description Opis notatki.
     * @param planedDeadline Planowany termin wykonania notatki.
     * @param team Zespół powiązany z notatką.
     */
    public void updateNote(Integer id, java.util.Date inputDate, Integer responsibleUser, String state, String type, String description, java.util.Date planedDeadline, Integer team) {
        Note currNote = new Note(id);

        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE_SQL)) {

            preparedStatement.setInt(1, responsibleUser);
            preparedStatement.setInt(6, team);
            preparedStatement.setString(2, state);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, description);
            java.sql.Date pDate = null;
            if (planedDeadline != null) {
                if (planedDeadline instanceof java.sql.Date) {
                    pDate = (java.sql.Date) planedDeadline;
                } else {
                    pDate = new java.sql.Date(planedDeadline.getTime());
                }
            }
            preparedStatement.setDate(5, pDate);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();

            if(Objects.equals(state, "zakończone")){
                Integer noteHistoryId = getNoteHistoryId(id);

                updateNoteHistory(noteHistoryId);
            } else if (!Objects.equals(currNote.getResponsibleUser(), responsibleUser)) {
                Integer noteHistoryId = getNoteHistoryId(id);

                updateNoteHistory(noteHistoryId);
                addNoteHistory(noteHistoryId,responsibleUser);
            }

        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    /**
     * Usuwa notatkę z bazy danych.
     *
     * @param id Identyfikator notatki do usunięcia.
     */
    public void deleteNote(Integer id) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE_SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    /**
     * Dodaje wpis historii notatki dla podanego ID notatki i ID użytkownika.
     *
     * @param noteId Identyfikator notatki.
     * @param userId Identyfikator użytkownika.
     */
    private void addNoteHistory(Integer noteId, Integer userId){
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NOTE_HISTORY_SQL)) {

            preparedStatement.setInt(1, noteId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    /**
     * Zwraca identyfikator historii notatki dla danego identyfikatora notatki.
     *
     * @param noteId Identyfikator notatki.
     * @return Identyfikator historii notatki, lub null, jeśli nie znaleziono.
     */
    private Integer getNoteHistoryId(Integer noteId){
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOTE_HISTORY_FOR_NOTEID_SQL)) {

            preparedStatement.setInt(1, noteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return null;
    }

    /**
     * Aktualizuje historię notatki w bazie danych.
     *
     * @param histId Identyfikator wpisu historii do zaktualizowania.
     */
    private void updateNoteHistory(Integer histId){
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE_HISTORY_SQL)) {

            preparedStatement.setInt(2, histId);
            preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

}
