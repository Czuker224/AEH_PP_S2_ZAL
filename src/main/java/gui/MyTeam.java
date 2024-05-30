package gui;

import session.Session;

import java.awt.event.*;
import javax.swing.*;

public class MyTeam extends AppWindow implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public MyTeam(Session session, AppWindow lastWindow) {
        super();

        setSession(session);
        prepareWindow();
    }

    private void prepareWindow(){
        initializeFrame();

        frame.setVisible(true);
    }

}
