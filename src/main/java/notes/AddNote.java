package notes;

import notes.Note;
import java.util.Date;

public class AddNote extends Note {

    public AddNote(Integer responsibleUser, String state, String type, String description, Date plannedDedline) {
        super(responsibleUser, state, type, description, plannedDedline);
    }

}
