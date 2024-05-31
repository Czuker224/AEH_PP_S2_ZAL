package user;

import db.user.UserRepository;

public class User {
    private String imie;
    private String nazwisko;
    private String email;
    private String haslo;
    private Integer Id;


    /* KONSTRUKTORY */

    /**
     * Represents a User with specified attributes.
     * This class provides constructors to create a User object with different parameters.
     */
    public User(Integer Id) {
        UserRepository ur = new UserRepository();
        User sUser = (User) ur.getUser(Id).getFirst();
        if (sUser != null) {
            this.imie = sUser.getImie();
            this.nazwisko = sUser.getNazwisko();
            this.email = sUser.getEmail();
            this.haslo = sUser.getHaslo();
            this.Id = Id;
        }else{
            this.Id = Id;
        }
    }

    /**
     * Constructs a new User object with the given email.
     *
     * @param email the email of the user
     */
    public User(String email) {
        UserRepository ur = new UserRepository();
        User sUser = (User) ur.getUser(Id).getFirst();
        if (sUser != null) {
            this.imie = sUser.getImie();
            this.nazwisko = sUser.getNazwisko();
            this.email = sUser.getEmail();
            this.haslo = sUser.getHaslo();
            this.Id = Id;
        }else{
            this.Id = Id;
        }
    }

    /**
     * Represents a user.
     */
    public User (String imie, String nazwisko, String email, String haslo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.haslo = haslo;
    }

    /**
     * Represents a User with their id, first name, last name, email, and password.
     */
    public User (Integer id,String imie, String nazwisko, String email, String haslo) {
        this.Id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.haslo = haslo;
    }


    /* SETERY I GETERY */

    /**
     * Sets the value of the "imie" field.
     *
     * @param imie The new value for the "imie" field.
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return the first name of the user
     */
    public String getImie() {
        return this.imie;
    }

    /**
     * Sets the value of the "nazwisko" property of the User object.
     *
     * @param nazwisko the new value for the "nazwisko" property
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * Retrieves the value of the "nazwisko" property.
     *
     * @return The value of the "nazwisko" property.
     */
    public String getNazwisko() {
        return this.nazwisko;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the password for the user.
     *
     * @param haslo The new password for the user.
     */
    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    /**
     * Retrieves the password associated with the user.
     *
     * @return the password of the user
     */
    public String getHaslo() {
        return this.haslo;
    }

    /**
     * Sets the ID of the User.
     *
     * @param Id the new ID of the User
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     * Retrieves the ID of the User.
     *
     * @return The ID of the User.
     */
    public Integer getId() {
        return this.Id;
    }


}
