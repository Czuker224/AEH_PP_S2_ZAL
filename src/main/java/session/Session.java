package session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import db.session.SessionRepository;

public class Session {
    private Integer id;
    private String token;
    private Integer userId;


    /**
     * The Session class represents a user session.
     *
     * A session is created by generating a unique token and setting it as the session's token.
     *
     * Class Fields:
     * - id: An integer representing the session's ID.
     * - token: A string representing the session's token.
     * - userId: An integer representing the user ID associated with the session.
     *
     * Class Methods:
     * - Session(): Constructor for the Session class. Initializes the session's token by calling the generateToken method.
     *
     * Private Methods:
     * - generateToken(): Generates a unique token for the session using the current date and time concatenated with a random number.
     *
     * Example Usage:
     * Session session = new Session();
     */
    public Session() {
        this.token = generateToken();
    }

    /**
     * Represents a user session.
     */
    public Session(Integer id,Integer userId, String token) {
        this.id = id;
        this.token = token;
        this.userId = userId;
    }

    /**
     * Initializes a new Session object for the specified user ID.
     *
     * If an active session exists for the specified user ID, the session properties (id, token, and userId)
     * will be set to the values of the active session retrieved from the database. Otherwise, a new session
     * will be created with a generated token and inserted into the database.
     *
     * @param userId the ID of the user
     */
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

    /**
     * Retrieves the ID of the session.
     *
     * @return The ID of the session represented by an Integer.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retrieves the token associated with the session.
     *
     * @return The token string.
     */
    public String getToken() {return token;}

    /**
     * Retrieves the user ID.
     *
     * @return the user ID stored in the userId field.
     */
    public Integer getUserId() {return userId;}

    /**
     * Check if the session is active for the given user ID.
     *
     * @param userId The ID of the user for which to check the session.
     * @return The token of the active session.
     */
    public String checkIsSessionActive(Integer userId){
        Session sessionToken = getActiveSession(userId);
        return sessionToken.getToken();
    }

    /**
     * The endSession method is used to end the current session for a user.
     * It marks the session associated with the provided token as inactive and prints a message to confirm the session has been ended.
     */
    public void endSession(){
        SessionRepository sr = new SessionRepository();
        sr.endSession(token);
        System.out.println("Zakonczono sesję: " + token);
    }

    /**
     * Prints the session information.
     *
     * Prints the ID, token, and user ID of the session to the standard output.
     */
    public void printSession() {
        System.out.println("Dane sessji:");
        System.out.println("ID: " + id);
        System.out.println("Token: " + token);
        System.out.println("User ID: " + userId);
        System.out.println("-------");
    }






    /**
     * Generates a token by combining the current date and time with a randomly generated number.
     *
     * @return The generated token as a string.
     */
    private String generateToken(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String datetimePart = currentDateTime.format(formatter);

        Random rand = new Random();
        String randomPart = String.format("%010d", rand.nextInt(1000000000));

        return datetimePart + randomPart;
    }

    /**
     * Inserts a new session into the session repository.
     *
     * This method creates a new session object and adds it to the session repository. The session object is created using
     * the provided token. The token is used to uniquely identify the session.
     *
     * Usage:
     *  insertNewSession();
     *
     * Example:
     *  Session session = new Session();
     *  session.insertNewSession();
     */
    private void insertNewSession(){
        SessionRepository sr = new SessionRepository();
        sr.addSession(token);
    }

    /**
     * Inserts a new session for the given user ID.
     *
     * @param userId the ID of the user for whom the session needs to be inserted
     */
    private void insertNewSession(Integer userId){
        SessionRepository sr = new SessionRepository();
        sr.addSession(token,userId);
    }

    /**
     * Retrieves the active session for a given user.
     *
     * @param userId the ID of the user
     * @return the active session for the user
     */
    private Session getActiveSession(Integer userId){
        SessionRepository sr = new SessionRepository();
        return sr.getActiveSession(userId);
    }
}
