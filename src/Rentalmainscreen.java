import javax.swing.*;
import java.awt.*;

public class Rentalmainscreen extends JFrame {
    private JPanel navbarPanel = new Navbar();

    public Rentalmainscreen() throws HeadlessException {
        super("Car Rental");
        this.initializeUI();
    }

    private void initializeUI() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(this.navbarPanel, BorderLayout.NORTH);

        JPanel subscreen = new JPanel();
        subscreen.setLayout(new BorderLayout());
        subscreen.setBackground(new Color(00, 250, 250));
        container.add(subscreen, BorderLayout.CENTER);


        ImageIcon imageIcon = new ImageIcon("C:/Users/ASUS/Desktop/EAD Cw1/src/Car-Rentals-in-India.jpg");


        Image scaledImage = imageIcon.getImage().getScaledInstance(750, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);


        JLabel imageLabel = new JLabel(scaledImageIcon);
        subscreen.add(imageLabel, BorderLayout.CENTER);

        JLabel tableTitle = new JLabel();
        tableTitle.setText("Login");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
        tableTitle.setHorizontalAlignment(SwingConstants.CENTER);
        subscreen.add(tableTitle, BorderLayout.NORTH);


        logIn signInPanel = new logIn();

        subscreen.add(signInPanel, BorderLayout.SOUTH);

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Rentalmainscreen();
        });
    }
}
