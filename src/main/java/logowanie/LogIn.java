package logowanie;

import user.User;

public class LogIn {
    private String email = null;
    private String psw = null;

    public LogIn(String email, String psw) {
        this.email = email;
        this.psw = psw;
    }

    public void printLogin(){
        System.out.println("Dane zapisane w klasie LogIn:");
        System.out.println("Email: " + email);
        System.out.println("Password: " + psw);
    }


}
