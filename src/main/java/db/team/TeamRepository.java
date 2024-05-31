package db.team;

import db.dbConnection;
import db.session.SessionRepository;
import team.Team;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Klasa TeamRepository dostarcza metody do interakcji z bazą danych oraz wykonywania operacji związanych z zespołami.
 */
public class TeamRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String SELECT_TEAM_BYID_SQL = "SELECT * FROM TEAM WHERE ID = ?";
    private static final String SELECT_TEAM_BYNAME_SQL = "SELECT * FROM TEAM WHERE NAME = ?";
    private static final String INSERT_TEAM_SQL = "INSERT INTO TEAM (Name) VALUES (?)";
    private static final String DELETE_TEAM_SQL = "DELETE FROM TEAM WHERE ID = ?";
    private static final String SELECT_TEAMS_BY_USER_SQL = "SELECT T.ID, T.NAME FROM TEAM T, MEMBERS M WHERE T.ID = M.team AND userId = ?";
    private static final String SELECT_USERS_BY_TEAM_SQL = "SELECT userId FROM MEMBERS WHERE team = ?";


    /**
     * Dodaje zespół do bazy danych.
     *
     * @param name Nazwa zespołu do dodania.
     * @return Wygenerowany klucz dla dodanego zespołu.
     */
    public int addTeam(String name) {
        ResultSet generatedKeys = null;
        int generatedKey = 0;
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEAM_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
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

    /**
     * Usuwa zespół z bazy danych na podstawie jego identyfikatora.
     *
     * @param id Identyfikator zespołu do usunięcia.
     */
    public void deleteTeam(int id) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEAM_SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    /**
     * Pobiera zespół z bazy danych na podstawie podanego identyfikatora.
     *
     * @param id Identyfikator zespołu do pobrania.
     * @return Zespół pobrany z bazy danych o podanym identyfikatorze.
     * @throws SQLException Jeśli wystąpi błąd podczas dostępu do bazy danych.
     * @throws IllegalArgumentException Jeśli podany identyfikator jest null.
     */
    public Team getTeamById(Integer id) throws SQLException {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }


        Team team = null;
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TEAM_BYID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer teamId = resultSet.getInt("id");
                String teamName = resultSet.getString("name");

                team  = new Team(teamId, teamName);
            }
        }

        assert team != null;
        team.setUsers(getUsersForTeam(team));


        return team;
    }

    /**
     * Pobiera obiekt zespołu z bazy danych na podstawie nazwy zespołu.
     *
     * @param name Nazwa zespołu.
     * @return Obiekt zespołu, jeśli istnieje w bazie danych, w przeciwnym razie null.
     * @throws SQLException jeśli wystąpi błąd dostępu do bazy danych.
     */
    public Team getTeamByName(String name) throws SQLException {

        if (name == null) {
            throw new IllegalArgumentException("id cannot be null");
        }


        Team team = null;
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TEAM_BYNAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer teamId = resultSet.getInt("id");
                String teamName = resultSet.getString("name");

                team  = new Team(teamId, teamName);
            }
        }

        assert team != null;
        team.setUsers(getUsersForTeam(team));


        return team;
    }

    /**
     * Pobiera listę zespołów powiązanych z użytkownikiem.
     *
     * @param userId Identyfikator użytkownika, dla którego mają być pobrane zespoły.
     * @return Lista obiektów zespołów powiązanych z użytkownikiem.
     */
    public List<Team> getTeams(int userId) {
        List<Team> teams = new ArrayList<>();
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TEAMS_BY_USER_SQL)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int teamId = resultSet.getInt("id");
                String teamName = resultSet.getString("name");
                Team team = new Team(teamId,teamName);
                teams.add(team);
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return teams;
    }

    /**
     * Pobiera listę użytkowników należących do określonego zespołu.
     *
     * @param team Zespół, dla którego mają być pobrani użytkownicy.
     * @return Lista obiektów użytkowników reprezentujących użytkowników należących do zespołu.
     */
    public List<User> getUsersForTeam(Team team) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_BY_TEAM_SQL)) {

            preparedStatement.setInt(1, team.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");

                User user = new User(userId);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return users;
    }
}
