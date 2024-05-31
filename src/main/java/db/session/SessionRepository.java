package db.session;

import db.dbConnection;
import session.Session;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Klasa obsługująca sesje w bazie danych
 */
public class SessionRepository {
    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String INSERT_SESSION1_SQL = "INSERT INTO SESSION (token, isActive, inputDate) VALUES (?, ?, ?)";
    private static final String INSERT_SESSION2_SQL = "INSERT INTO SESSION (token, userId, isActive, inputDate) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SESSION_USER_SQL = "UPDATE SESSION SET UserId = ? WHERE UserId = ?";
    private static final String UPDATE_SESSION_END_SQL = "UPDATE SESSION SET endDate = ?, isActive = 0 WHERE token = ?";
    private static final String SELECT_SESSION_TOKEN_SQL = "SELECT token FROM SESSION WHERE isActive = 1 AND UserId = ? ORDER BY inputDate DESC LIMIT 1";
    private static final String SELECT_SESSION_SQL = "SELECT * FROM SESSION WHERE isActive = 1 AND UserId = ? ORDER BY inputDate DESC LIMIT 1";

    /**
     * Dodaje nową sesję do bazy danych.
     *
     * @param token Token powiązany z sesją.
     */
    public void addSession(String token) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SESSION1_SQL)) {

            preparedStatement.setString(1, token);
            preparedStatement.setInt(2, 1);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    /**
     * Dodaje sesję do bazy danych.
     *
     * @param token Token sesji.
     * @param userId Identyfikator użytkownika powiązany z sesją.
     */
    public void addSession(String token,Integer userId) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SESSION2_SQL)) {

            preparedStatement.setString(1, token);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, 1);
            preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    /**
     * Pobiera aktywny token sesji dla danego identyfikatora użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @return Aktywny token sesji dla użytkownika, lub null, jeśli nie zostanie znaleziona aktywna sesja.
     */
    public String getActiveSessionToken(int userId) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareGetSessionTokenQuery(connection, userId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             return handleGetTokenResault(resultSet);
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return null;
    }

    /**
     * Pobiera aktywną sesję dla danego identyfikatora użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @return Aktywna sesja dla użytkownika.
     * Jeśli nie zostanie znaleziona aktywna sesja, zwraca null.
     */
    public Session getActiveSession(int userId) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareGetSessionQuery(connection, userId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return handleGetSesionResault(resultSet);
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return null;
    }

    /**
     * Aktualizuje użytkownika powiązanego z sesją.
     *
     * @param sessionId Identyfikator sesji.
     * @param userId    Identyfikator użytkownika, który ma być powiązany z sesją.
     */
    public void updateSessionUser(String sessionId, int userId) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareUpdateSessionUserQuery(connection, sessionId, userId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    /**
     * Kończy sesję poprzez aktualizację czasu zakończenia sesji w bazie danych.
     *
     * @param token Token sesji, która ma zostać zakończona.
     */
    public void endSession(String token) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareEndSessionQuery(connection, token)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }


    /**
     * Przygotowuje zapytanie w celu pobrania tokenu sesji dla danego identyfikatora użytkownika.
     *
     * @param connection połączenie z bazą danych
     * @param userId identyfikator użytkownika
     * @return przygotowane wyrażenie dla zapytania
     * @throws SQLException jeśli wystąpi błąd podczas przygotowywania zapytania
     */
    private PreparedStatement prepareGetSessionTokenQuery(Connection connection, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SESSION_TOKEN_SQL);
        preparedStatement.setInt(1, userId);
        return preparedStatement;
    }

    /**
     * Przygotowuje PreparedStatement do pobrania sesji z bazy danych dla danego identyfikatora użytkownika.
     *
     * @param connection obiekt Connection bazy danych
     * @param userId     identyfikator użytkownika, dla którego jest pobierana sesja
     * @return przygotowane wyrażenie do zapytania o sesję
     * @throws SQLException jeśli wystąpi błąd dostępu do bazy danych
     */
    private PreparedStatement prepareGetSessionQuery(Connection connection, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SESSION_SQL);
        preparedStatement.setInt(1, userId);
        return preparedStatement;
    }

    /**
     * Obsługuje wynik zapytania w celu pobrania tokenu sesji z bazy danych.
     *
     * @param resultSet Zestaw wyników zawierający token sesji.
     * @return Token sesji, jeśli istnieje w zestawie wyników, w przeciwnym razie null.
     * @throws SQLException Jeśli wystąpi błąd podczas dostępu do zestawu wyników.
     */
    private String handleGetTokenResault(ResultSet resultSet) throws SQLException {
        String sessionToken = null;
        if (resultSet.next()) {
            sessionToken = resultSet.getString("token");
        }
        return sessionToken;
    }

    /**
     * Obsługuje wynik zapytania do bazy danych w celu pobrania obiektu sesji.
     *
     * @param resultSet ResultSet zawierający wynik zapytania.
     * @return Obiekt sesji pobrany z wyniku zapytania, lub null, jeśli nie znaleziono wyniku.
     * @throws SQLException Jeśli wystąpi błąd podczas pobierania danych sesji z ResultSet.
     */
    private Session handleGetSesionResault(ResultSet resultSet) throws SQLException {
        Session session = null;
        if (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String token = resultSet.getString("token");
            Date inputDate = resultSet.getDate("inputDate");
            Integer UserId = resultSet.getInt("UserId");

            session = new Session(id,UserId,token);
        }
        return session;
    }

    /**
     * Przygotowuje PreparedStatement do aktualizacji użytkownika sesji.
     *
     * @param connection Połączenie z bazą danych.
     * @param sessionId Identyfikator sesji.
     * @param userId Identyfikator użytkownika.
     * @return Przygotowane wyrażenie do aktualizacji użytkownika sesji.
     * @throws SQLException Jeśli wystąpi błąd dostępu do bazy danych.
     */
    private PreparedStatement prepareUpdateSessionUserQuery(Connection connection, String sessionId, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SESSION_USER_SQL);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, sessionId);
        return preparedStatement;
    }

    /**
     * Przygotowuje zapytanie do zakończenia sesji.
     *
     * @param connection Połączenie z bazą danych.
     * @param token      Token sesji do zakończenia.
     * @return Przygotowane wyrażenie dla zakończenia sesji.
     * @throws SQLException Jeśli wystąpi błąd podczas przygotowywania wyrażenia.
     */
    private PreparedStatement prepareEndSessionQuery(Connection connection, String token) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SESSION_END_SQL);
        preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
        preparedStatement.setString(2, token);
        return preparedStatement;
    }
}
