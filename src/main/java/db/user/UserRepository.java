package db.user;

import db.dbConnection;
import db.session.SessionRepository;
import user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String SELECT_USER_CREDITIONALS_SQL = "SELECT * FROM USER WHERE EMAIL = ? AND HASLO = ?";
    private static final String SELECT_USER_SQL = "SELECT * FROM USER WHERE ID = ?";

    public List checkUserCredentials(String email, String password) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareUserCredentialsQuery(connection, email, password);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return handleQueryResults(resultSet);
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return null;
    }

    public List getUser(Integer id) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareGetUserQuery(connection, id);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return handleQueryResults(resultSet);
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return null;
    }

    private PreparedStatement prepareUserCredentialsQuery(Connection connection, String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_CREDITIONALS_SQL);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        return preparedStatement;
    }

    private PreparedStatement prepareGetUserQuery(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    private List handleQueryResults(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {

            Integer id = resultSet.getInt("id");
            String userName = resultSet.getString("imie");
            String userSurname = resultSet.getString("nazwisko");
            String userEmail = resultSet.getString("email");
            String password = resultSet.getString("haslo");

            User user = new User(id,userName, userSurname, userEmail, password);
            users.add(user);
        }
        return users;
    }

}
