import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class ownerForm extends JPanel {

    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JTextField telephone;
    private JTextField Password;

    public ownerForm() {
        setLayout(new GridLayout(6,2));

        JLabel firstNameLabel = new JLabel("First Name:");
        add(firstNameLabel);
        firstNameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        firstName = new JTextField(20);
        add(firstName);
        firstName.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel lastNameLabel = new JLabel("Last Name:");
        add(lastNameLabel);
        lastNameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        lastName = new JTextField(20);
        add(lastName);
        lastName.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel);
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        email = new JTextField(20);
        add(email);
        email.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        Password = new JTextField(20);
        add(Password);
        Password.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel telephoneLabel = new JLabel("Telephone:");
        add(telephoneLabel);
        telephoneLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        telephone = new JTextField(20);
        add(telephone);
        telephone.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFocusPainted(false);
        JButton backBtn = new JButton("Back");
        backBtn.setFocusPainted(false);
        add(submitBtn);
        add(backBtn);

        submitBtn.addMouseListener(new HoverMouseListener());
        backBtn.addMouseListener(new HoverMouseListener());

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }

        });
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertDataIntoDatabase();
            }
        });

        this.setSize(400, 200);
        this.setVisible(true);
    }
    private void back() {

        Rentalmainscreen mainscreen = new Rentalmainscreen();
        mainscreen.setVisible(true);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }
    private boolean validateFields() {

        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || email.getText().isEmpty() ||
                telephone.getText().isEmpty() || Password.getText().isEmpty()) {
            return false;
        }

        if (!isValidEmail(email.getText())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address");
            return false;
        }

        if (!isNumeric(telephone.getText())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid telephone number");
            return false;
        }

        return true;
    }


    private boolean isValidEmail(String email) {

        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private void insertDataIntoDatabase() {
        if (!validateFields()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }


        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO owners (firstName, lastName, email, telephone, Password) VALUES ( ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, firstName.getText());
                preparedStatement.setString(2, lastName.getText());
                preparedStatement.setString(3, email.getText());
                preparedStatement.setString(4, telephone.getText());
                preparedStatement.setString(5, Password.getText());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee details inserted successfully");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert employee details");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        telephone.setText("");
        Password.setText("");
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
        SwingUtilities.invokeLater(() -> new ownerForm());
    }
}
