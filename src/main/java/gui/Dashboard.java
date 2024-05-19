package gui;

import db.notes.NoteRepository;
import notes.Note;
import notes.Notes;
import session.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Dashboard extends AppWindow implements ActionListener {

    private JPanel panelNotes;
    private JButton buttonAddNewNotte;

    public Dashboard(Session session){
        super();

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

    private void addNewNote () {
        this.frame.dispose();
        new AddNote(session,this);
    }

    private void editNote(Note note){
        this.frame.dispose();
        new AddNote(session,note,this);
    }
}
