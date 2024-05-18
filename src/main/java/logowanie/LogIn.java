package logowanie;

import user.User;
import db.user.UserRepository;
import java.util.List;
import session.Session;

public class LogIn {
    private String email = null;
    private String psw = null;
    private Integer id = null;

    public LogIn(String email, String psw) {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.checkUserCredentials(email, psw);

        if (!users.isEmpty()) {
            User user = users.get(0);
            setId(user.getId());
            setEmail(user.getEmail());
            setPsw(user.getHaslo());
            printLogin();
        } else {
            System.out.println("Email lub haslo niepoprawne");
        }
    }


    public Integer getId() {
        return this.id;
    }

    public void printLogin(){
        System.out.println("Dane zapisane w klasie LogIn:");
        System.out.println("Id: " + id);
        System.out.println("Email: " + email);
        System.out.println("Password: " + psw);
    }

    private void setId(Integer id) {
        this.id = id;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPsw(String psw) {
        this.psw = psw;
    }






}
