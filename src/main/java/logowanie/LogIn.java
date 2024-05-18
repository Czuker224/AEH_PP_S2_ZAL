package logowanie;

import user.User;
import db.user.UserRepository;
import java.util.List;

public class LogIn {
    private String email = null;
    private String psw = null;

    public LogIn(String email, String psw) {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.checkUserCredentials(email, psw);

        if (!users.isEmpty()) {
            User user = users.get(0);
            this.email = user.getEmail();
            this.psw = user.getHaslo();
            printLogin();
        } else {
            System.out.println("Email lub haslo niepoprawne");
        }
    }

    public void printLogin(){
        System.out.println("Dane zapisane w klasie LogIn:");
        System.out.println("Email: " + email);
        System.out.println("Password: " + psw);
    }


}
