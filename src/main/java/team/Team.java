package team;

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

    public Team(String name, Integer id){
        this.name = name;
        this.Id = id;
    }

    public Team(String name, User user){
        this.name = name;
        this.users = new ArrayList<>();
        users.add(user);
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
    public List getMyTeams (User user){

        List<Team> teams = new ArrayList<>();
        return teams;
    }


    /* private */

}
