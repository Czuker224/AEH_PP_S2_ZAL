package gui;

import session.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AppWindow implements ActionListener {

    protected AppWindow lastWindow;
    protected Session session;
    protected GridBagLayout layout;
    protected GridBagConstraints constraints;
    protected JFrame frame;

    protected void setSession(Session session) {
        if(session == null){
            System.out.println("brak sesji");
        }
        this.session = session;
    }

    protected void initializeFrame() {
        frame = new JFrame("Boskie notatki");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                onClose();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setSize(1400, 900);

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        frame.setLayout(layout);
    }

    protected void onClose() {

        this.frame.dispose();
        if(this.lastWindow != null){
            this.lastWindow.frame.setVisible(true);
            return;
        }
        this.session.endSession();
    }

}
