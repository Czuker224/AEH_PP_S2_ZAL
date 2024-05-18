import notes.AddNote;
import session.Session;
import logowanie.LogIn;
import user.User;

import java.util.Scanner;
import java.util.Date;


public class App {

    public static void main( String[] args)
    {
        User currentUser = null;
        Session currentSession = null;

        //logowanie

        if (currentUser == null){
            Scanner sc = new Scanner(System.in);
            System.out.println("Podaj e-mail: ");
            String inpEmail = sc.nextLine();

            System.out.println("Podaj hasło: ");
            String inpPas = sc.nextLine();

            LogIn logIn = new LogIn(inpEmail, inpPas);
            if (logIn.getId() != null) {
                currentUser = new User(logIn.getId());
            }
        }

        // Utworzenie sesji dla zalogowanych użytkowników

        if(currentSession == null && currentUser.getId() != null){

            //TO DO: dodać timeout dla sesji

            currentSession = new Session(currentUser.getId());
        }


        AddNote note = new AddNote(1,"New","Ważne-Pilne","testowa notatka sprawdzająca",new Date());
        note.save();
        note.printNote();



        //Zakończenie sesji
        if(currentSession != null){
            currentSession.endSession();
        }
    }
}
