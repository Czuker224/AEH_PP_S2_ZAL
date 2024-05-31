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

    public Note(){
        this.creationDate = new Date();
        this.state = "do zrobienia";
    }

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(Integer responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public java.util.Date getPlannedDedline() {
        return plannedDedline;
    }

    public void setPlannedDedline(Date plannedDedline) {
        this.plannedDedline = plannedDedline;
    }

    public Integer getTeam() { return team;}

    public void setTeam(Integer team) {this.team = team;}


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
