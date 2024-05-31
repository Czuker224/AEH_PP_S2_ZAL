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

/**
 * Klasa UserRepository udostępnia metody do interakcji z bazą danych i wykonywania operacji na danych użytkowników.
 */
public class UserRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String SELECT_USER_CREDITIONALS_SQL = "SELECT * FROM USER WHERE EMAIL = ? AND HASLO = ?";
    private static final String SELECT_USER_SQL = "SELECT * FROM USER WHERE ID = ?";
    private static final String SELECT_USER_BYEMAIL_SQL = "SELECT * FROM USER WHERE EMAIL = ?";

    /**
     * Sprawdza poświadczenia użytkownika, wyszukując w bazie danych na podstawie podanego adresu e-mail i hasła.
     *
     * @param email Adres e-mail użytkownika
     * @param password Hasło użytkownika
     * @return Lista obiektów użytkowników odpowiadających podanemu adresowi e-mail i hasłu. Zwraca pustą listę, jeśli nie znaleziono dopasowania.
     */
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

    /**
     * Pobiera listę użytkowników na podstawie podanego identyfikatora użytkownika.
     *
     * @param id Identyfikator użytkownika.
     * @return Lista obiektów użytkowników odpowiadających podanemu identyfikatorowi, lub null, jeśli operacja na bazie danych się nie powiedzie.
     */
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

    /**
     * Pobiera obiekt użytkownika z bazy danych na podstawie podanego adresu e-mail.
     *
     * @param email Adres e-mail użytkownika do pobrania.
     * @return Obiekt użytkownika, jeśli został znaleziony, null jeśli nie został znaleziony lub wystąpił wyjątek.
     */
    public User getUserByEmail(String email) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = prepareGetUserQuery(connection, email);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return handleQueryResults_byEmail(resultSet);
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return null;
    }

    /**
     * Przygotowuje przygotowane wyrażenie do zapytania o dane uwierzytelniające użytkownika na podstawie adresu e-mail i hasła.
     *
     * @param connection Połączenie z bazą danych
     * @param email Adres e-mail użytkownika
     * @param password Hasło użytkownika
     * @return Przygotowane wyrażenie do zapytania o dane uwierzytelniające użytkownika
     * @throws SQLException Jeśli wystąpi błąd dostępu do bazy danych
     */
    private PreparedStatement prepareUserCredentialsQuery(Connection connection, String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_CREDITIONALS_SQL);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        return preparedStatement;
    }

    /**
     * Przygotowuje zapytanie SQL do pobrania użytkownika po jego ID.
     *
     * @param connection obiekt połączenia z bazą danych
     * @param id ID użytkownika do pobrania
     * @return obiekt przygotowanego wyrażenia do wykonania zapytania
     * @throws SQLException jeśli wystąpi błąd bazy danych
     */
    private PreparedStatement prepareGetUserQuery(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    /**
     * Przygotowuje zapytanie do bazy danych w celu pobrania użytkownika po jego adresie e-mail.
     *
     * @param connection aktywne połączenie z bazą danych
     * @param email adres e-mail użytkownika do pobrania
     * @return obiekt PreparedStatement do wykonania zapytania
     * @throws SQLException jeśli wystąpi błąd dostępu do bazy danych
     */
    private PreparedStatement prepareGetUserQuery(Connection connection, String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BYEMAIL_SQL);
        preparedStatement.setString(1, email);
        return preparedStatement;
    }

    /**
     * Obsługuje wyniki zapytania z bazy danych i konwertuje je na listę obiektów użytkowników.
     *
     * @param resultSet wynik zapytania do bazy danych
     * @return Lista obiektów użytkowników reprezentujących wyniki zapytania
     * @throws SQLException jeśli wystąpi błąd podczas dostępu do wyniku zapytania
     */
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

    /**
     *
     * Analizuje ResultSet zwrócony z zapytania do bazy danych i konstruuje obiekt użytkownika na podstawie adresu e-mail.
     *
     * @param resultSet Obiekt ResultSet zawierający dane pobrane z zapytania do bazy danych.
     *                  ResultSet powinien zawierać następujące kolumny:
     *                  - id: Unikalny identyfikator użytkownika
     *                  - imie: Imię użytkownika
     *                  - nazwisko: Nazwisko użytkownika
     *                  - email: Adres e-mail użytkownika
     *                  - haslo: Hasło użytkownika
     * @return Obiekt User reprezentujący użytkownika o podanym adresie e-mail, lub null, jeśli użytkownik nie został znaleziony.
     * @throws SQLException Jeśli wystąpi błąd podczas analizowania ResultSet.
     */
    private User handleQueryResults_byEmail(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Integer userId = resultSet.getInt("id");
            String userName = resultSet.getString("imie");
            String userSurname = resultSet.getString("nazwisko");
            String userEmail = resultSet.getString("email");
            String userPassword = resultSet.getString("haslo");

            return new User(userId,userName, userSurname, userEmail, userPassword);
        }
        return null;
    }

}
