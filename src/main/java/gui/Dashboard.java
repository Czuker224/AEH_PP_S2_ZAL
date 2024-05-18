package gui;

import session.Session;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard implements ActionListener {

    public Dashboard(Session session){
        if(session == null){
            System.out.println("brak sesji");
        }

        JFrame frame = new JFrame("Boskie notatki");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1400, 900);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        frame.setLayout(layout);

        JLabel Message = new JLabel();
        Message.setText("good job");
        frame.add(Message, constraints);

        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

    }

}
