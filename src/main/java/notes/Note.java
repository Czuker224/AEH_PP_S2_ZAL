package notes;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import db.notes.NoteRepository;

public class Note {
    public Integer id;
    public Integer team;
    public Integer responsibleUser;
    public String state;
    public String type;
    public String description;
    public java.util.Date creationDate;
    public java.util.Date plannedDedline;

    private String startingState;
    private Integer startingResponsibleUser;

//    public final List<String> TYPE_LIST = Arrays.asList("Ważne-Pilne", "Ważne-Niepilne", "Nieważne-Pilne", "Nieważne-Niepilne");
    public final String[] TYPE_LIST = {"Ważne-Pilne", "Ważne-Niepilne", "Nieważne-Pilne", "Nieważne-Niepilne"};

    /**
     * Default constructor for the Note class. Initializes the creation date to the current date
     * and sets the state to "do zrobienia".
     */
    public Note(){
        this.creationDate = new Date();
        this.state = "do zrobienia";
    }

    /**
     * Creates a new Note object with the given details.
     *
     * @param responsibleUser the ID of the responsible user for the note
     * @param state the state of the note
     * @param type the type of the note
     * @param description the description of the note
     * @param plannedDeadline the planned deadline for the note
     */
    public Note(Integer responsibleUser, String state, String type, String description, Date plannedDeadline) {
        this.responsibleUser = responsibleUser;
        this.state = state;
        this.type = type;
        this.description = description;
        this.creationDate = new Date();;
        this.plannedDedline = plannedDeadline;

        if(startingState == null){
            startingState = state;
        }

        if (startingResponsibleUser == null){
            startingResponsibleUser = responsibleUser;
        }
    }

    /**
     * Initializes a new instance of the Note class with the given parameters.
     *
     * @param id              The identifier of the note. Cannot be null.
     * @param responsibleUser The identifier of the user responsible for the note.
     * @param state           The state of the note.
     * @param type            The type of the note.
     * @param description     The description of the note.
     * @param plannedDeadline The planned deadline for the note.
     * @param team            The identifier of the team associated with the note.
     */
    public Note(Integer id, Integer responsibleUser, String state, String type, String description, Date plannedDeadline,Integer team) {
        this.id = id;
        this.responsibleUser = responsibleUser;
        this.state = state;
        this.type = type;
        this.description = description;
        this.creationDate = new Date();;
        this.plannedDedline = plannedDeadline;
        this.team = team;

        if(startingState == null){
            startingState = state;
        }
        if (startingResponsibleUser == null){
            startingResponsibleUser = responsibleUser;
        }
    }

    /**
     * Creates a Note object with the provided id.
     * Retrieves the note details from the NoteRepository using the given id and populates the fields of the Note object.
     * If the retrieved note is not null, the fields of the current Note object will be updated with the retrieved values.
     * Additionally, if the startingState and startingResponsibleUser fields are null, they will be set to the current state and responsible user respectively.
     *
     * @param id The id of the note to be retrieved from the NoteRepository.
     */
    public Note(Integer id){
        NoteRepository repo = new NoteRepository();
        Note note = repo.getNote(id);

        if(note != null){
            this.id = note.getId();
            this.responsibleUser = note.getResponsibleUser();
            this.state = note.getState();
            this.type = note.getType();
            this.description = note.getDescription();
            this.creationDate = note.getCreationDate();
            this.plannedDedline = note.getPlannedDedline();

            if(startingState == null){
                startingState = state;
            }
            if (startingResponsibleUser == null){
                startingResponsibleUser = responsibleUser;
            }
        }
    }


    /**
     * Returns the id of the Note.
     *
     * @return the id of the Note
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the Note.
     *
     * @param id the ID to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the responsible user for the note.
     *
     * @return The responsible user as an Integer.
     */
    public Integer getResponsibleUser() {
        return responsibleUser;
    }

    /**
     * Sets the responsible user for the Note object.
     *
     * @param responsibleUser the ID of the responsible user to be set for the Note.
     */
    public void setResponsibleUser(Integer responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    /**
     * Retrieves the state of the object.
     *
     * @return The state of the object as a String.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the Note.
     *
     * @param state the new state of the Note
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Retrieves the type of the Note object.
     *
     * @return The type of the Note.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the note.
     *
     * @param type the type of the note
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the description of the note.
     *
     * @return the description of the note
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Note.
     *
     * @param description the new description to be set for the Note
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the creation date of the Note.
     *
     * @return The creation date of the Note as a java.util.Date object.
     */
    public java.util.Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the note.
     *
     * @param creationDate the creation date of the note
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Retrieves the planned deadline.
     *
     * @return The planned deadline as a java.util.Date object.
     */
    public java.util.Date getPlannedDedline() {
        return plannedDedline;
    }

    /**
     * Sets the planned deadline for the note.
     *
     * @param plannedDeadline the planned deadline for the note
     */
    public void setPlannedDedline(Date plannedDedline) {
        this.plannedDedline = plannedDedline;
    }

    /**
     * Retrieves the team associated with this note.
     *
     * @return The team associated with this note as an Integer value.
     */
    public Integer getTeam() { return team;}

    /**
     * Sets the team for the note.
     *
     * @param team the team to set for the note. Accepts an integer value representing the team.
     */
    public void setTeam(Integer team) {this.team = team;}


    /**
     * Saves the current Note object to the NoteRepository. If the Note object has an id, it updates the existing Note entry.
     * If the Note object doesn't have an id, it creates a new Note entry in the NoteRepository and sets the id of the Note object.
     * The Note object properties used for saving are as follows:
     * - creationDate
     * - responsibleUser
     * - state
     * - type
     * - description
     * - plannedDedline
     * - team
     */
    public void save(){
        NoteRepository req = new NoteRepository();

        if(this.id == null){
            java.sql.Date sqlCreationDate = null;
            java.sql.Date sqlPlannedDedline = null;

            if(getCreationDate() != null){
                sqlCreationDate = new java.sql.Date(getCreationDate().getTime());
            }
            if(getPlannedDedline() != null){
                sqlPlannedDedline = new java.sql.Date(getPlannedDedline().getTime());
            }

            Integer id = req.addNote(sqlCreationDate, getResponsibleUser(), getState(), getType(), getDescription(), sqlPlannedDedline,getTeam());
            setId(id);
        }

        req.updateNote(getId(),getCreationDate(),getResponsibleUser(),getState(),getType(),getDescription(),getPlannedDedline(),getTeam());
    }


    /**
     * Prints the details of a note.
     * This method retrieves the ID, responsible user, state, type, description, creation date, and planned deadline of the note
     * and prints them to the console.
     */
    public void printNote() {
        System.out.println("ID: " + getId());
        System.out.println("Responsible User: " + getResponsibleUser());
        System.out.println("State: " + getState());
        System.out.println("Type: " + getType());
        System.out.println("Description: " + getDescription());
        System.out.println("Creation Date: " + getCreationDate());
        System.out.println("Planned Deadline: " + getPlannedDedline());
    }
}
