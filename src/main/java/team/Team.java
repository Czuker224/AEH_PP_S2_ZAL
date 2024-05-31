package team;

import db.notes.NoteRepository;
import db.team.TeamRepository;
import user.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private Integer Id;
    private List<User> users;

    /* KONSTRUKTORY */

    /**
     * Represents a team.
     *
     * @param name the name of the team
     */
    public Team(String name){

        this.name = name;
    }

    /**
     * Class representing a team.
     */
    public Team(Integer id) throws SQLException {
        TeamRepository repo = new TeamRepository();
        Team repoTeam = repo.getTeamById(id);

        if (repoTeam != null) {
            name = repoTeam.getName();
            Id = repoTeam.getId();
            users = repoTeam.getUsers();
        }else{
            this.Id = id;
        }
    }

    /**
     * The Team class represents a team consisting of users.
     * It can have a name, an id, and a list of users.
     */
    public Team(){

    }

    /**
     * Represents a team.
     */
    public Team(Integer id,String name){
        this.Id = id;
        this.name = name;
    }

    /**
     * Constructs a Team object.
     *
     * @param name the name of the team
     * @param user the user to be added to the team
     */
    public Team(String name, User user){
        this.name = name;
        this.users = new ArrayList<>();
        users.add(user);
    }

    /**
     * Constructs a new Team object with the given id, name, and list of users.
     *
     * @param id    the team id
     * @param name  the team name
     * @param users the list of users in the team
     */
    public Team(Integer id, String name, List<User> users){
        this.Id = id;
        this.name = name;
        this.users = users;
    }


    /**
     * Retrieves the name of the team.
     *
     * @return The name of the team.
     */
    /* getery i setery */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the team.
     *
     * @param name the new name for the team
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the ID of the team.
     *
     * @return the ID of the team.
     */
    public Integer getId() {
        return this.Id;
    }

    /**
     * Sets the ID of the Team.
     *
     * @param Id the ID to be set
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     * Retrieves the list of users associated with this Team.
     *
     * @return List of User objects representing the users associated with this Team.
     */
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * Sets the list of users for the team.
     *
     * @param users The list of users to set for the team.
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }



    /* PUBLIC */

    /**
     * Retrieves the teams associated with a given user.
     *
     * @param user The user whose teams are to be retrieved.
     * @return A list of Team objects representing the teams associated with the given user.
     */
    public static List<Team> getMyTeams(User user){
        TeamRepository tr = new TeamRepository();
        return tr.getTeams(user.getId());
    }



    /* PRIVATE */

}
