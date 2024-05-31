package db.reports;

import db.dbConnection;
import db.session.SessionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Klasa ReportsRepository dostarcza metod do pobierania różnych raportów z bazy danych.
 */
public class ReportsRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());

    private static final String SELECT_NOTE_COUNT_WORKTIME_SQL = "SELECT NH.noteId, NH.userId, SUM((CAST(IFNULL(NH.dateEnd, strftime('%s', 'now') * 1000) AS INTEGER) - CAST(NH.dateStart AS INTEGER))) [TIME] " +
            "FROM NOTES N, NOTES_HISTORY NH " +
            "WHERE N.id = NH.noteId AND N.state NOT IN ('zakończone') AND NH.noteId = ? AND NH.userId = ? " +
            "GROUP BY NH.noteId, NH.userId";

    private static final String SELECT_USER_COUNT_ALLOPENNOTES_SQL = "SELECT COUNT(id) [COUNT] FROM NOTES WHERE state NOT IN ('zakończone') AND responsibleUser = ?";
    private static final String SELECT_TEAM_COUNT_ALLOPENNOTES_SQL = "SELECT COUNT(id) [COUNT] FROM NOTES WHERE state NOT IN ('zakończone') AND team = ?";




    /**
     * Zlicza liczbę otwartych notatek dla danego użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @return Liczba otwartych notatek dla użytkownika. Jeśli wystąpi błąd lub brak danych, zwracana jest wartość null.
     */
    public Integer userCountAllOpenNotes(Integer userId){
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_COUNT_ALLOPENNOTES_SQL)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                return resultSet.getInt("COUNT");
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }

        return null;
    }

    /**
     * Zlicza liczbę otwartych notatek dla danego zespołu.
     *
     * @param teamId Identyfikator zespołu.
     * @return Liczba otwartych notatek dla zespołu. Jeśli wystąpi błąd lub nie znaleziono danych, zwracana jest wartość null.
     */
    public Integer teamCountAllOpenNotes(Integer teamId){
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TEAM_COUNT_ALLOPENNOTES_SQL)) {

            preparedStatement.setInt(1, teamId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                return resultSet.getInt("COUNT");
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }

        return null;
    }

    /**
     * Zlicza czas pracy dla konkretnej notatki i użytkownika.
     *
     * @param noteId Identyfikator notatki.
     * @param userId Identyfikator użytkownika.
     * @return Całkowity czas pracy dla danej notatki i użytkownika, w minutach. Jeśli wystąpi błąd lub brak danych, zwracana jest wartość null.
     */
    public Long noteCountWorkTime(Integer noteId, Integer userId) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOTE_COUNT_WORKTIME_SQL)) {

            preparedStatement.setInt(1, noteId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("TIME");
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }

        return null;
    }

}
