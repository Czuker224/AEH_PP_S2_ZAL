package session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import db.session.SessionRepository;

public class Session {
    private Integer id;
    private String token;
    private Integer userId;


    public Session() {
        this.token = generateToken();
    }

    public Session(Integer id,Integer userId, String token) {
        this.id = id;
        this.token = token;
        this.userId = userId;
    }

    public Session(Integer userId) {
        //sprawdzenie czy istnieje aktywna sesja
        Session activeSession = getActiveSession(userId);
        if (activeSession != null) {
            this.id = activeSession.getId();
            this.token = activeSession.getToken();
            this.userId = activeSession.getUserId();

            System.out.println("używam tokenu aktywnej sesji z bazy danych (" + activeSession + ")");
        } else {
            this.token = generateToken();
            this.insertNewSession(userId);

            Session updateActiveSession = getActiveSession(userId);
            if(updateActiveSession != null){
                this.id = updateActiveSession.getId();
                this.token = updateActiveSession.getToken();
                this.userId = updateActiveSession.getUserId();
            }

            System.out.println("Session: " + token);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {return token;}

    public Integer getUserId() {return userId;}

    public String checkIsSessionActive(Integer userId){
        Session sessionToken = getActiveSession(userId);
        return sessionToken.getToken();
    }

    public void endSession(){
        SessionRepository sr = new SessionRepository();
        sr.endSession(token);
        System.out.println("Zakonczono sesję: " + token);
    }

    public void printSession() {
        System.out.println("Dane sessji:");
        System.out.println("ID: " + id);
        System.out.println("Token: " + token);
        System.out.println("User ID: " + userId);
        System.out.println("-------");
    }






    private String generateToken(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String datetimePart = currentDateTime.format(formatter);

        Random rand = new Random();
        String randomPart = String.format("%010d", rand.nextInt(1000000000));

        return datetimePart + randomPart;
    }

    private void insertNewSession(){
        SessionRepository sr = new SessionRepository();
        sr.addSession(token);
    }

    private void insertNewSession(Integer userId){
        SessionRepository sr = new SessionRepository();
        sr.addSession(token,userId);
    }

    private Session getActiveSession(Integer userId){
        SessionRepository sr = new SessionRepository();
        return sr.getActiveSession(userId);
    }
}
