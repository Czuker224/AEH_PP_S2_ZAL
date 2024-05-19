package gui;

import session.Session;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn implements ActionListener {
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel logoPlaceholder;
    private JLabel errorMessage;
    private User user;
    private Session session;

    public LogIn(User user, Session session) {
        this.user = user;
        this.session = session;

        frame = new JFrame("Logowanie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 400);

        // Set layout to GridBagLayout for better control over components
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        frame.setLayout(layout);

        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(150, 30)); // Set preferred size
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(150, 30)); // Set preferred size
        passwordField.setEchoChar('*');
        loginButton = new JButton("Log in");
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);

        // Add components with GridBagLayout constraints
        constraints.fill = GridBagConstraints.HORIZONTAL; // Horizontal expansion
        constraints.gridx = 0;  // x = 0
        constraints.gridy = 0;  // y = 0
        constraints.anchor = GridBagConstraints.CENTER; // Position : center of the area
        frame.add(new JLabel("Email: "), constraints);

        constraints.gridy++; // y = 1
        frame.add(emailField, constraints);

        constraints.gridy++; // y = 2
        frame.add(new JLabel("Password: "), constraints);

        constraints.gridy++; // y = 3
        frame.add(passwordField, constraints);

        constraints.gridy++; // y = 4
        frame.add(errorMessage, constraints);

        constraints.gridy++; // y = 5
        frame.add(loginButton, constraints);

        loginButton.addActionListener(this);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            logInUser(this.user,this.session);
        }
    }

    private void logInUser(User user, Session session) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        System.out.println(email + "," + password);

        logowanie.LogIn logIn = new logowanie.LogIn(email, password);
        if (logIn.getId() != null) {
            user = new User(logIn.getId());
        }

        // Utworzenie sesji dla zalogowanych użytkowników
        if(user == null){
            return;
        }

        if(user != null && user.getId() != null){

            //TO DO: dodać timeout dla sesji

            session = new Session(user.getId());
        }

        if(session == null){

            errorMessage.setText("Invalid email or password");
            errorMessage.setVisible(true);

            return;
        }

        errorMessage.setText("");
        frame.dispose();

        session.printSession();
        new Dashboard(session);
    }

    private void endProgram(){

    }

}
