package notes;

import db.notes.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class Notes {

    public static List<notes.Note> getMyNotes(Integer userId){
        NoteRepository nr = new NoteRepository();
        List<notes.Note> myNotes = nr.getUserNotes(userId);
        return myNotes;
    }
}
