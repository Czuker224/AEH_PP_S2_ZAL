package gui;

import db.team.TeamRepository;
import db.user.UserRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNote extends AppWindow implements ActionListener {

    private JButton buttonAddNewNotte;
    private JComboBox<String> cbCategory;
    private JComboBox<String> cbTeam;
    private JComboBox<String> cbResponsiblePerson;
//    private JTextArea txtAreaDescription;
    private JTextField txtAreaDescription;
    private JTextField txtFieldEndDate;

    private User currentUser;
    private Note currentNote;
    private Team selectedTeam;
    private List<Team> MyTeams;

    public void actionPerformed(ActionEvent e) {

    }

    public AddNote(Session session,AppWindow lastWindow){
        super();

        this.currentNote = new Note();
        this.lastWindow = lastWindow;

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
        currentUser = new User(session.getUserId());

        initializeFrame();

        // Create and set up label and combo box for "Zespół"
        MyTeams = Team.getMyTeams(new User(session.getUserId()));

//        List<Team> MyTeams = currentUser.getTeamList();
        String[] teamOptions = new String[MyTeams.size()];
        for (int i = 0; i < MyTeams.size(); i++) {
            teamOptions[i] = ((Team) MyTeams.get(i)).getName();
        }
        constraints.gridx = 0;
        constraints.gridy = 0;
        setLabbel("Zespół", constraints);
        cbTeam = new JComboBox<>(teamOptions);
        cbTeam.setPreferredSize(new Dimension(200, 50));
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
            constraints.gridx = 1;
            frame.add(cbCategory, constraints);

        // Create and set up label and text field for "Opis"
        constraints.gridx = 0;
        constraints.gridy = 2;
        setLabbel("Opis",constraints);
//        txtAreaDescription = new JTextArea(5, 20);
        txtAreaDescription = new JTextField(300);
        txtAreaDescription.setPreferredSize(new Dimension(1000, 50));
        constraints.gridx = 1;
            frame.add(txtAreaDescription, constraints);
            if (currentNote.description != null) {
                txtAreaDescription.setText(currentNote.description);
            }

        // Create and set up label and date chooser for "Planowana data zakończenia"
        constraints.gridx = 0;
        constraints.gridy = 4;
        setLabbel("Planowana data wykonania",constraints);
            txtFieldEndDate = new JFormattedTextField(DateFormat.getDateInstance());
            constraints.gridx = 1;
            frame.add(txtFieldEndDate, constraints);
            if (currentNote.description != null) {
                txtFieldEndDate.setText(currentNote.getPlannedDedline().toString());
            }

        // Create and set up label and combo box for "Osoba odpowiedzialna"
        prepareUserComboBox();
//        frame.add(cbResponsiblePerson, constraints);


        // Create and set up the "Save" button
        buttonAddNewNotte = new JButton("Zapisz");
            buttonAddNewNotte.addActionListener(e -> {
                try {
                    addNewNote();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridwidth = 1;
            frame.add(buttonAddNewNotte, constraints);


        frame.setVisible(true);

    }

    private void prepareUserComboBox() {

        List<User> users = new ArrayList<>();
        for(Team team : MyTeams){
            if(team.getUsers() != null){
                users.addAll(team.getUsers());
            }
        }
        if(users.isEmpty()){
            users.add(new User(session.getUserId()));
        }

        
        for (User user : users) {
            System.out.println(user.getEmail());
        }

        String[] responsiblePersonOptions = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            responsiblePersonOptions[i] = users.get(i).getEmail();
        }

            constraints.gridx = 0;
            constraints.gridy = 5;
            setLabbel("Osoba odpowiedzialna",constraints);
            cbResponsiblePerson = new JComboBox<>(responsiblePersonOptions);


            if (currentNote.description != null) {
                cbResponsiblePerson.setSelectedItem(currentNote.getResponsibleUser());
            }
            constraints.gridx = 1;
            frame.add(cbResponsiblePerson, constraints);
//        }
    }

    private void setLabbel(String text, GridBagConstraints constr ){
        JLabel lbl = new JLabel(text);
        frame.add(lbl, constr);
    }


    private void addNewNote() throws SQLException {

        this.currentNote.setDescription(txtAreaDescription.getText());
        this.currentNote.setState("New");

        if(cbCategory.getSelectedItem() != null){
            this.currentNote.setType(cbCategory.getSelectedItem().toString());
        }

        if(cbResponsiblePerson.getSelectedItem() != null){
            UserRepository ur = new UserRepository();
            User xUser = ur.getUserByEmail(cbResponsiblePerson.getSelectedItem().toString());
            this.currentNote.setResponsibleUser(xUser.getId());
        }

        if(cbTeam.getSelectedItem() != null){
            TeamRepository tr = new TeamRepository();
            Team team = tr.getTeamByName(cbTeam.getSelectedItem().toString());
            this.currentNote.setTeam(team.getId());
        }

        if(txtFieldEndDate != null && !txtFieldEndDate.getText().isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate date = LocalDate.parse(txtFieldEndDate.getText(), formatter);
                this.currentNote.setPlannedDedline(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(frame, "Please make sure the date is in the format yyyy-MM-dd", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        this.currentNote.save();
        this.frame.dispose();
        new Dashboard(session);
//        this.lastWindow.frame.setVisible(true);

    }
}
