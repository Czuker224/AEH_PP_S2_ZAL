
import gui.Dashboard;
import session.Session;
import user.User;

public class App {

    public static void main( String[] args)
    {
        User currentUser = null;
        Session currentSession = null;

        //logowanie
        if (currentUser == null){

            new gui.LogIn(currentUser, currentSession);


        }
        new Dashboard(currentSession);

    }
}
