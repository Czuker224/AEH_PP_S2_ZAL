package db.session;

import db.dbConnection;
import session.Session;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SessionRepository {
    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String INSERT_SESSION1_SQL = "INSERT INTO SESSION (token, isActive, inputDate) VALUES (?, ?, ?)";
    private static final String INSERT_SESSION2_SQL = "INSERT INTO SESSION (token, userId, isActive, inputDate) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SESSION_USER_SQL = "UPDATE SESSION SET UserId = ? WHERE UserId = ?";
    private static final String UPDATE_SESSION_END_SQL = "UPDATE SESSION SET endDate = ?, isActive = 0 WHERE token = ?";
    private static final String SELECT_SESSION_TOKEN_SQL = "SELECT token FROM SESSION WHERE isActive = 1 AND UserId = ? ORDER BY inputDate DESC LIMIT 1";
    private static final String SELECT_SESSION_SQL = "SELECT * FROM SESSION WHERE isActive = 1 AND UserId = ? ORDER BY inputDate DESC LIMIT 1";

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

    public void updateSessionUser(String sessionId, int userId) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareUpdateSessionUserQuery(connection, sessionId, userId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    public void endSession(String token) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareEndSessionQuery(connection, token)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }


    private PreparedStatement prepareGetSessionTokenQuery(Connection connection, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SESSION_TOKEN_SQL);
        preparedStatement.setInt(1, userId);
        return preparedStatement;
    }

    private PreparedStatement prepareGetSessionQuery(Connection connection, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SESSION_SQL);
        preparedStatement.setInt(1, userId);
        return preparedStatement;
    }

    private String handleGetTokenResault(ResultSet resultSet) throws SQLException {
        String sessionToken = null;
        if (resultSet.next()) {
            sessionToken = resultSet.getString("token");
        }
        return sessionToken;
    }

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

    private PreparedStatement prepareUpdateSessionUserQuery(Connection connection, String sessionId, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SESSION_USER_SQL);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, sessionId);
        return preparedStatement;
    }

    private PreparedStatement prepareEndSessionQuery(Connection connection, String token) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SESSION_END_SQL);
        preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
        preparedStatement.setString(2, token);
        return preparedStatement;
    }
}
