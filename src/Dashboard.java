import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {

    public Dashboard() {
        JPanel navbarPanel = new Navbar();

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JButton logout=new JButton("Log Out");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        add(navbarPanel, BorderLayout.NORTH);
        add(logout, BorderLayout.SOUTH);

        JButton button1 = createButton("           Daily Rental          ");

        JButton button3 = createButton("              Payments           ");
        JButton button4 = createButton("              Customers          ");


        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(button1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        buttonPanel.add(button3);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(button4);
        buttonPanel.add(Box.createVerticalGlue());


        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        button1.setFont(buttonFont);
        button3.setFont(buttonFont);
        button4.setFont(buttonFont);

        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.add(Box.createHorizontalGlue());
        horizontalPanel.add(buttonPanel);
        horizontalPanel.add(Box.createHorizontalGlue());

        add(horizontalPanel, BorderLayout.CENTER);

        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                daily();
            }
        });


        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payment();
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customer();
            }
        });
    }

    private static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(250, 100));
        return button;
    }

    private void daily() {
        dispose();
        Daily daily = new Daily();
        daily.setVisible(true);
    }
    private void logout() {
        dispose();
        Rentalmainscreen logout = new Rentalmainscreen();
        logout.setVisible(true);
    }

    private void monthly() {
        dispose();
        Monthly monthly = new Monthly();
        monthly.setVisible(true);
    }
    private void customer() {
        dispose();
        CustomerMain cus = new CustomerMain();
        cus.setVisible(true);
    }


    private void payment() {
        dispose();
        PaymentMain paymentMain = new PaymentMain();
        paymentMain.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard());
    }
}
