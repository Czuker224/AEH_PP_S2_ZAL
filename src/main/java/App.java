import notes.AddNote;
import notes.Note;
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
        if(currentUser == null){
            return;
        }

        if(currentSession == null && currentUser.getId() != null){

            //TO DO: dodać timeout dla sesji

            currentSession = new Session(currentUser.getId());
        }

        if(currentSession == null){
            return;
        }



//        AddNote note = new AddNote(1,"New","Ważne-Pilne","testowa notatka sprawdzająca",new Date());
//        note.save();
//        note.printNote();


        Note note = new Note(1);
        if(note.getId() != null){
            note.printNote();
        }else{
            System.out.println("Brak danych do wyświetlenia");
        }




        //Zakończenie sesji
        if(currentSession != null){
            currentSession.endSession();
        }
    }
}
