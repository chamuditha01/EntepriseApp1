import javax.swing.*;
import java.awt.*;

public class RegisterUI extends JFrame {
    private JPanel navbarPanel = new Navbar();
    public RegisterUI() throws HeadlessException {
        super("Car Rental");
        this.initializeUI();
    }
    public void initializeUI(){
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(this.navbarPanel, BorderLayout.NORTH);

        JPanel subscreen = new JPanel();
        subscreen.setLayout(new BorderLayout());
        subscreen.setBackground(new Color(00, 250, 250));
        container.add(subscreen, BorderLayout.CENTER);

        JLabel tableTitle = new JLabel();
        tableTitle.setText("Register");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
        tableTitle.setHorizontalAlignment(SwingConstants.CENTER);
        subscreen.add(tableTitle, BorderLayout.NORTH);
        JLabel lbl = new JLabel("                        Easy For Rental Owners");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 30));
        lbl.setBackground(new Color(196, 164, 132));
        lbl.setOpaque(true);
        subscreen.add(lbl, BorderLayout.SOUTH);

        ownerForm owner=new ownerForm();
        subscreen.add(owner, BorderLayout.CENTER);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterUI();
        });
    }

}
