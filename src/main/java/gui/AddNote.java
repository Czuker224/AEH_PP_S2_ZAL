package gui;

import db.team.TeamRepository;
import notes.Note;
import session.Session;
import team.Team;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;

public class AddNote extends AppWindow implements ActionListener {

    private JButton buttonAddNewNotte;
    private JComboBox<String> cbCategory;
    private JComboBox<String> cbTeam;
    JComboBox<String> cbResponsiblePerson;
//    private JTextArea txtAreaDescription;
    private JTextField txtAreaDescription;


    private Note currentNote;
    private Team selectedTeam;

    public void actionPerformed(ActionEvent e) {

    }

    public AddNote(Session session,AppWindow lastWindow){
        super();

        this.currentNote = new Note();

        setSession(session);
        prepareWindow();
    }

    public AddNote(Session session, Note note, AppWindow lastWindow){
        super();

        this.currentNote = note;
        this.lastWindow = lastWindow;

        setSession(session);
        prepareWindow();

    }

    private void prepareWindow(){
        initializeFrame();

        // Create and set up label and combo box for "Zespół"
        List MyTeams = Team.getMyTeams(new User(session.getUserId()));
        String[] teamOptions = new String[MyTeams.size()];
        for (int i = 0; i < MyTeams.size(); i++) {
            teamOptions[i] = ((Team) MyTeams.get(i)).getName();
        }
        constraints.gridx = 0;
        constraints.gridy = 0;
        setLabbel("Zespół", constraints);
        cbTeam = new JComboBox<>(teamOptions);
        if (currentNote.team != null) {
            cbTeam.setSelectedItem(currentNote.team);
        }
        constraints.gridx = 1;
        cbTeam.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                    String selectedTeamName = (String) cbTeam.getSelectedItem();
                try {
                    selectedTeam = new TeamRepository().getTeamByName(selectedTeamName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                prepareUserComboBox();
            }
        });

        frame.add(cbTeam, constraints);

        // Create and set up label and combo box for "Kategoria"
        String[] categoryOptions = currentNote.TYPE_LIST;
        constraints.gridx = 0;
        constraints.gridy = 1;
        setLabbel("Kategoria",constraints);
            cbCategory = new JComboBox<>(categoryOptions);
            if (currentNote.type != null) {
                cbCategory.setSelectedItem(currentNote.type);
            }
            constraints.gridx = 2;
            frame.add(cbCategory, constraints);

        // Create and set up label and text field for "Opis"
        constraints.gridx = 0;
        constraints.gridy = 2;
        setLabbel("Opis",constraints);
//        txtAreaDescription = new JTextArea(5, 20);
        txtAreaDescription = new JTextField(150);
        constraints.gridx = 3;
            frame.add(txtAreaDescription, constraints);
            if (currentNote.description != null) {
                txtAreaDescription.setText(currentNote.description);
            }

        // Create and set up label and date chooser for "Planowana data zakończenia"
        constraints.gridx = 0;
        constraints.gridy = 4;
        setLabbel("Planowana data wykonania",constraints);
            JTextField txtFieldEndDate = new JFormattedTextField(DateFormat.getDateInstance());
            constraints.gridx = 4;
            frame.add(txtFieldEndDate, constraints);
            if (currentNote.description != null) {
                txtFieldEndDate.setText(currentNote.getPlannedDedline().toString());
            }

        // Create and set up label and combo box for "Osoba odpowiedzialna"
        //prepareUserComboBox();
        frame.add(cbResponsiblePerson, constraints);


        // Create and set up the "Save" button
        buttonAddNewNotte = new JButton("Zapisz");
            buttonAddNewNotte.addActionListener(e -> addNewNote());
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
            frame.add(buttonAddNewNotte, constraints);


        frame.setVisible(true);

    }

    private void prepareUserComboBox() {

//        if(selectedTeam != null){
            List<User> TeamUser = selectedTeam.getUsers();
            String[] responsiblePersonOptions = new String[TeamUser.size()];
            for (int i = 0; i < TeamUser.size(); i++) {
                responsiblePersonOptions[i] = ((User) TeamUser.get(i)).getEmail();
            }

            if(responsiblePersonOptions.length > 0 ){
                constraints.gridx = 0;
                constraints.gridy = 5;
                setLabbel("Osoba odpowiedzialna",constraints);

                if (currentNote.description != null) {
                    cbResponsiblePerson.setSelectedItem(currentNote.getResponsibleUser());
                }
                constraints.gridx = 5;

            }
//        }
    }

    private void setLabbel(String text, GridBagConstraints constr ){
        JLabel lbl = new JLabel(text);
        frame.add(lbl, constr);
    }


    private void addNewNote() {
        this.currentNote.setDescription(txtAreaDescription.getText());
        this.currentNote.setType(cbCategory.getSelectedItem().toString());

        this.currentNote.save();
        this.frame.dispose();
        this.lastWindow.frame.setVisible(true);

    }
}
