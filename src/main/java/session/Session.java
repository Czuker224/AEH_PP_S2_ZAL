package session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import db.session.SessionRepository;

public class Session {
    private String sessionId;
    private String sessionToken;

    public Session() {
        this.sessionId = generateToken();
    }

    public Session(Integer userId) {
        //sprawdzenie czy istnieje aktywna sesja
        String activeSession = getActiveSession(userId);
        if (activeSession != null) {
            this.sessionId = activeSession;

            System.out.println("używam tokenu aktywnej sesji z bazy danych (" + activeSession + ")");
        } else {
            this.sessionId = generateToken();
            this.insertNewSession(userId);

            System.out.println("Session: " + this.sessionId);
        }
    }

    public String getSesionId() {
        return this.sessionId;
    }

    public String checkIsSessionActive(Integer userId){
        String sessionToken = getActiveSession(userId);
        return sessionToken;
    }

    public void endSession(){
        SessionRepository sr = new SessionRepository();
        sr.endSession(this.sessionId);
        System.out.println("Zakonczono sesję: " + this.sessionId);
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
        sr.addSession(this.sessionId);
    }

    private void insertNewSession(Integer userId){
        SessionRepository sr = new SessionRepository();
        sr.addSession(this.sessionId,userId);
    }

    private String getActiveSession(Integer userId){
        SessionRepository sr = new SessionRepository();
        return sr.getActiveSessionToken(userId);
    }
}
