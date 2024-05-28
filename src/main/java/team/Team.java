package team;

import db.notes.NoteRepository;
import db.team.TeamRepository;
import user.User;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private Integer Id;
    private List<User> users;


    /* konstruktory */
    public Team(String name){
        this.name = name;
    }

    public Team(Integer id){
        this.Id = id;
    }

    public Team(Integer id,String name){
        this.Id = id;
        this.name = name;
    }

    public Team(String name, User user){
        this.name = name;
        this.users = new ArrayList<>();
        users.add(user);
    }

    public Team(Integer id, String name, List<User> users){
        this.Id = id;
        this.name = name;
        this.users = users;
    }


    /* getery i setery */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



    /* public */
    public static List<Team> getMyTeams(User user){
        TeamRepository tr = new TeamRepository();
        return tr.getTeams(user.getId());
    }



    /* private */

}
