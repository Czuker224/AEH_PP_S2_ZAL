import session.Session;

public class App {

    public static void main( String[] args)
    {
        String currentSession = "";

        if(currentSession == ""){
            Session session = new Session();
            currentSession = session.getSesionId();
        }

        System.out.println("Session: " + currentSession);

    }

}
