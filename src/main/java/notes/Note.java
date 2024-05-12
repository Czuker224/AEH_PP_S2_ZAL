package notes;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import db.notes.NoteRepository;

public class Note {

    public Integer id;
    public Integer responsibleUser;
    public String state;
    public String type;
    public String description;
    public java.util.Date creationDate;
    public java.util.Date plannedDedline;

    public static final List<String> TYPE_LIST = Arrays.asList("Ważne-Pilne", "Ważne-Niepilne", "Nieważne-Pilne", "Nieważne-Niepilne");

    public Note(Integer responsibleUser, String state, String type, String description, Date plannedDedline) {
        this.responsibleUser = responsibleUser;
        this.state = state;
        this.type = type;
        this.description = description;
        this.creationDate = new Date();;
        this.plannedDedline = plannedDedline;
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


    public void save(){
        NoteRepository req = new NoteRepository();
        java.sql.Date sqlCreationDate = new java.sql.Date(this.getCreationDate().getTime());
        java.sql.Date sqlPlannedDedline = new java.sql.Date(this.getPlannedDedline().getTime());
        Integer id = req.addNote(sqlCreationDate, this.getResponsibleUser(), this.getState(), this.getType(), this.getDescription(), sqlPlannedDedline);
        this.setId(id);
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
