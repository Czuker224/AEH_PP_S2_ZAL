package notes;

import notes.Note;
import java.util.Date;

public class AddNote extends Note {

    /**
     * Adds a new note with the given details.
     *
     * @param responsibleUser the ID of the responsible user for the note
     * @param state the state of the note
     * @param type the type of the note
     * @param description the description of the note
     * @param plannedDeadline the planned deadline for the note
     */
    public AddNote(Integer responsibleUser, String state, String type, String description, Date plannedDedline) {
        super(responsibleUser, state, type, description, plannedDedline);
    }

}
