import notes.AddNote;
import session.Session;
import logowanie.LogIn;

import java.util.Scanner;
import java.util.Date;


public class App {

    public static void main( String[] args)
    {
        String currentSession = "";
        Integer currentUserId = null;

        if (currentUserId == null){
            Scanner sc = new Scanner(System.in);
            System.out.println("Podaj e-mail: ");
            String inpEmail = sc.nextLine();

            System.out.println("Podaj hasło: ");
            String inpPas = sc.nextLine();

            LogIn logIn = new LogIn(inpEmail, inpPas);
            logIn.printLogin();

            //TO DO: Dodać opcję pobrania usera z bazy danych

            //TO DO: przypisać pobranego usera do currentUserId
        }

        if(currentSession == ""){

            //TO DO: przypisać userId do sesji

            //TO DO: dodać timeout dla sesji
            //TO DO: dodać znacznik czy sesja jest aktywna

            //TO DO: dodać sprawdzenie czy sesja już jest ustawiona dla użytkownika

            Session session = new Session();
            currentSession = session.getSesionId();
        }

        System.out.println("Session: " + currentSession);

        AddNote note = new AddNote(1,"New","Ważne-Pilne","testowa notatka sprawdzająca",new Date());
        note.save();
        note.printNote();



    }
}
