package db.team;

import db.dbConnection;
import db.session.SessionRepository;
import notes.Note;
import team.Team;
import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TeamRepository {

    private static final Logger logger = Logger.getLogger(SessionRepository.class.getName());
    private static final String INSERT_TEAM_SQL = "INSERT INTO TEAM (Name) VALUES (?)";
    private static final String DELETE_TEAM_SQL = "DELETE FROM TEAM WHERE ID = ?";
    private static final String SELECT_TEAMS_BY_USER_SQL = "SELECT T.ID, T.NAME FROM TEAM T, MEMBERS M WHERE T.ID = M.team AND userId = ?";
    private static final String SELECT_USERS_BY_TEAM_SQL = "SELECT userId FROM MEMBERS WHERE team = ?";


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

    public void deleteTeam(int id) {
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TEAM_SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
    }

    public List<Team> getTeams(int userId) {
        List<Team> teams = new ArrayList<>();
        try (Connection connection = dbConnection.createDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TEAMS_BY_USER_SQL)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int teamId = resultSet.getInt("team_id");
                String teamName = resultSet.getString("team_name");
                Team team = new Team(teamName, teamId);
                teams.add(team);
            }
        } catch (SQLException e) {
            logger.severe("Database operation failed: " + e);
        }
        return teams;
    }

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
