package logowanie;

import user.User;
import db.user.UserRepository;
import java.util.List;
import session.Session;

/**
 * Klasa obsługująca model logowania do aplikacji
 * */
public class LogIn {
    private String email = null;
    private String psw = null;
    private Integer id = null;

    /**
     * Metoda LogIn służy do uwierzytelniania użytkownika poprzez sprawdzenie jego adresu e-mail i hasła w przechowywanych poświadczeniach użytkownika.
     *
     * @param email Adres e-mail użytkownika.
     * @param psw Hasło użytkownika.
     */
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


    /**
     * Pobiera identyfikator użytkownika.
     *
     * @return identyfikator użytkownika
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Wyświetla w konsoli szczegóły logowania przechowywane w klasie LogIn.
     */
    public void printLogin(){
        System.out.println("Dane zapisane w klasie LogIn:");
        System.out.println("Id: " + id);
        System.out.println("Email: " + email);
        System.out.println("Password: " + psw);
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param id identyfikator do ustawienia
     */
    private void setId(Integer id) {
        this.id = id;
    }

    /**
     * Ustawia adres e-mail dla użytkownika.
     *
     * @param email Adres e-mail, który ma zostać ustawiony dla użytkownika.
     */
    private void setEmail(String email) {
        this.email = email;
    }

    /**
     * Ustawia hasło dla użytkownika.
     *
     * @param psw hasło, które ma zostać ustawione dla użytkownika
     */
    private void setPsw(String psw) {
        this.psw = psw;
    }

}
