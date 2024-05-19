package gui;

import db.notes.NoteRepository;
import notes.Note;
import notes.Notes;
import session.Session;
import user.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Dashboard implements ActionListener {

    private Session session;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JFrame frame;
    private JPanel panelNotes;
    private JButton buttonAddNewNotte;

    public Dashboard(Session session){
        if(session == null){
            System.out.println("brak sesji");
        }
        this.session = session;

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        initializeFrame();

        // Adding the button to the frame
        buttonAddNewNotte = addNoteButton(frame);


        panelNotes = new JPanel();
            constraints.gridx = 0;
            constraints.gridy = 2;
            frame.add(panelNotes,constraints);
            renderNotes();

        frame.setVisible(true);

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
        constraints.fill = GridBagConstraints.HORIZONTAL;
        frame.setLayout(layout);
    }

    private JButton addNoteButton(JFrame parent){
        // Creating the button
        JButton btn = new JButton("Dodaj");
        btn.setBounds(0, 0, 70, 30);
        btn.setBackground(Color.BLUE);
        btn.setForeground(Color.WHITE);

        // Adding an ActionListener to the button
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewNote();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        parent.add(btn,constraints);
        return btn;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAddNewNotte) {
            addNewNote();
        }

        renderNotes();
    }

    private List getNotes (){
        NoteRepository nr = new NoteRepository();
        return nr.getUserNotes(this.session.getUserId());
    }

    private void renderNotes (){
        JPanel panel = this.panelNotes;
        panel.setLayout(new GridLayout(0, 1));

        List<Note> myNotes = Notes.getMyNotes(session.getUserId());
        if(myNotes != null){
            for (Note note : myNotes) {
                JLabel label = new JLabel(note.getDescription());
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        editNote(note);
                    }
                });
                panel.add(label);
            }
        }else{
            JLabel Message = new JLabel();
            Message.setText("Brawo!!! Nie ma zadań dla Ciebie");
            frame.add(Message, constraints);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane, constraints);
    }




    private void onClose() {

        this.frame.dispose();
        this.session.endSession();
    }

    // Method to handle the button click (addNewNote)
    private void addNewNote () {
        new AddNote(session);
    }

    private void editNote(Note note){
        new AddNote(session,note);
    }
}
