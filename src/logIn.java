import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class logIn extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public logIn() {
        setLayout(new BorderLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        usernameField.setFont(new Font("SansSerif", Font.BOLD, 20));
        passwordField.setFont(new Font("SansSerif", Font.BOLD, 20));

        RoundButton logInButton = new RoundButton("Log In");
        RoundButton signInButton = new RoundButton("Register");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(logInButton);
        buttonPanel.add(signInButton);
        buttonPanel.setBackground(new Color(57,107,116));
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(buttonPanel);
        formPanel.setBackground(new Color(57,107,116));
        add(formPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, new Color(57, 107, 116)));

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signIn();
            }
        });

        logInButton.addMouseListener(new HoverMouseListener());
        signInButton.addMouseListener(new HoverMouseListener());
    }

    private void logIn() {
        String enteredEmail = usernameField.getText();
        char[] enteredPasswordChars = passwordField.getPassword();
        String enteredPassword = new String(enteredPasswordChars);


        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT Password FROM owners WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, enteredEmail);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String hashedPasswordFromDB = resultSet.getString("Password");


                        if (hashedPasswordFromDB.equals(enteredPassword)) {

                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                            frame.dispose();

                            Dashboard dashboard = new Dashboard();
                            dashboard.setVisible(true);

                        }else {
                            JOptionPane.showMessageDialog(this, "Incorrect password. Please try again.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "User not found. Please register.");
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.");
        }
    }




    private void signIn() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();

        RegisterUI addEmployeeForm = new RegisterUI();
        addEmployeeForm.setVisible(true);
    }

    private class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {

            } else {
                g.setColor(getBackground());
            }
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }
    }

    private class HoverMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new logIn());
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

