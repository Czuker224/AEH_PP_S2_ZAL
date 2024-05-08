package session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import db.session.SessionRepository;

public class Session {
    private String sessionId;

    public Session() {
        this.sessionId = generateToken();

        this.insertNewSession();
    }

    public String getSesionId() {
        return this.sessionId;
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
}
