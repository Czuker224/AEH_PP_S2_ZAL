package gui;

import db.notes.NoteRepository;
import notes.Note;
import session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;

public class AddNote implements ActionListener {

    private Session session;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JFrame frame;
    private JButton buttonAddNewNotte;


    private Note currentNote;

    public void actionPerformed(ActionEvent e) {

    }

    public AddNote(Session session){
        this.currentNote = new Note();

        setSession(session);
        prepareWindow();
    }

    public AddNote(Session session, Note note){
        this.currentNote = note;

        setSession(session);
        prepareWindow();
    }

    private void prepareWindow(){
        initializeFrame();

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        frame.setLayout(layout);

        if(currentNote != null){
            System.out.println("Wybrana notatka: " + currentNote.getId());
        }


        // Create and set up label and combo box for "Kategoria"
        JLabel lblCategory = new JLabel("Kategoria");
        String[] categoryOptions = {"Option 1", "Option 2", "Option 3", "Option 4"};
        JComboBox<String> cbCategory = new JComboBox<>(categoryOptions);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        frame.add(lblCategory, constraints);
        constraints.gridx = 1;
        frame.add(cbCategory, constraints);

        // Create and set up label and text field for "Opis"
        JLabel lblDescription = new JLabel("Opis");
        JTextArea txtAreaDescription = new JTextArea(5, 20);
        constraints.gridx = 0;
        constraints.gridy = 1;
        frame.add(lblDescription, constraints);
        constraints.gridx = 1;
        frame.add(txtAreaDescription, constraints);

        // Create and set up label and date chooser for "Planowana data zakończenia"
        JLabel lblPlanEndDate = new JLabel("Planowana data zakończenia");
        JFormattedTextField txtFieldEndDate = new JFormattedTextField(DateFormat.getDateInstance());
        constraints.gridx = 0;
        constraints.gridy = 2;
        frame.add(lblPlanEndDate, constraints);
        constraints.gridx = 1;
        frame.add(txtFieldEndDate, constraints);

        // Create and set up label and combo box for "Osoba odpowiedzialna"
        JLabel lblResponsiblePerson = new JLabel("Osoba odpowiedzialna");
        String[] responsiblePersonOptions = {"Osoba 1", "Osoba 2", "Osoba 3"};
        JComboBox<String> cbResponsiblePerson = new JComboBox<>(responsiblePersonOptions);
        constraints.gridx = 0;
        constraints.gridy = 3;
        frame.add(lblResponsiblePerson, constraints);
        constraints.gridx = 1;
        frame.add(cbResponsiblePerson, constraints);

        // Create and set up the "Save" button
        buttonAddNewNotte = new JButton("Zapisz");
        buttonAddNewNotte.addActionListener(e -> addNewNote());
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        frame.add(buttonAddNewNotte, constraints);


        frame.setVisible(true);

    }

    private void setSession(Session session) {
        if(session == null){
            System.out.println("brak sesji");
        }
        this.session = session;
    }

    private void initializeFrame() {
        frame = new JFrame("Boskie notatki");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                onClose();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setSize(1400, 900);
    }

    private void onClose() {

        this.frame.dispose();
        this.session.endSession();
    }



    private void addNewNote() {

    }
}
