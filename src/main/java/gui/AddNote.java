package gui;

import notes.Note;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

public class AddNote extends AppWindow implements ActionListener {

    private JButton buttonAddNewNotte;
    private JComboBox<String> cbCategory;
//    private JTextArea txtAreaDescription;
    private JTextField txtAreaDescription;


    private Note currentNote;

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

        // Create and set up label and combo box for "Kategoria"
        String[] categoryOptions = currentNote.TYPE_LIST;
        constraints.gridx = 0;
        constraints.gridy = 0;
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
        txtAreaDescription = new JTextField(150);
        constraints.gridx = 1;
            frame.add(txtAreaDescription, constraints);
            if (currentNote.description != null) {
                txtAreaDescription.setText(currentNote.description);
            }

        // Create and set up label and date chooser for "Planowana data zakończenia"
        constraints.gridx = 0;
        constraints.gridy = 2;
        setLabbel("Planowana data wykonania",constraints);
            JTextField txtFieldEndDate = new JFormattedTextField(DateFormat.getDateInstance());
            constraints.gridx = 1;
            frame.add(txtFieldEndDate, constraints);
            if (currentNote.description != null) {
                txtFieldEndDate.setText(currentNote.getPlannedDedline().toString());
            }

        // Create and set up label and combo box for "Osoba odpowiedzialna"
        String[] responsiblePersonOptions = {"Opcja 1","Opcja 2","Opcja 3"};
            constraints.gridx = 0;
            constraints.gridy = 3;
            setLabbel("Osoba odpowiedzialna",constraints);
            JComboBox<String> cbResponsiblePerson = new JComboBox<>(responsiblePersonOptions);
            if (currentNote.description != null) {
                cbResponsiblePerson.setSelectedItem(currentNote.getResponsibleUser());
            }
            constraints.gridx = 1;
            frame.add(cbResponsiblePerson, constraints);


        // Create and set up the "Save" button
        buttonAddNewNotte = new JButton("Zapisz");
            buttonAddNewNotte.addActionListener(e -> addNewNote());
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 1;
            frame.add(buttonAddNewNotte, constraints);


        frame.setVisible(true);

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
