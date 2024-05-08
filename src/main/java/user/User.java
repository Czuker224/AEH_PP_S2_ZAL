package user;

public class User {
    private String imie;
    private String nazwisko;
    private String email;
    private String haslo;
    private Integer Id;


    /* konstruktory */

    public User(Integer Id) {
        this.Id = Id;
    }

    public User (String imie, String nazwisko, String email, String haslo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.haslo = haslo;
    }


    /* setery i getery */

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return this.imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNazwisko() {
        return this.nazwisko;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getHaslo() {
        return this.haslo;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return this.Id;
    }


}
