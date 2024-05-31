package notes;

import db.notes.NoteRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Ta klasa dostarcza statyczne metody do pobierania notatek należących do określonego użytkownika.
 */
public class Notes {

    /**
     * Pobiera notatki należące do określonego użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @return Lista notatek należących do użytkownika.
     */
    public static List<notes.Note> getMyNotes(Integer userId){
        NoteRepository nr = new NoteRepository();
        List<notes.Note> myNotes = nr.getUserNotes(userId);
        return myNotes;
    }
}
