import javax.swing.*;
import java.awt.*;

public class CustomerMain extends JFrame {
    private JPanel navbarPanel;


    public CustomerMain() throws HeadlessException {
        super("Nibm");
        this.navbarPanel = new Navbar();
        initializeUI();
    }

    private void initializeUI() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(navbarPanel, BorderLayout.NORTH);

        JPanel subscreen = new JPanel();
        subscreen.setLayout(new BorderLayout());

        JLabel tableTitle = new JLabel();
        tableTitle.setText("Customer Table");
        tableTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        tableTitle.setHorizontalAlignment(SwingConstants.CENTER);


        tableTitle.setOpaque(true);
        tableTitle.setBackground(new Color(196, 164, 132));

        subscreen.add(tableTitle, BorderLayout.NORTH);


        Customer databaseTableExample = new Customer();
        subscreen.add(databaseTableExample.getContentPane(), BorderLayout.CENTER);

        container.add(subscreen, BorderLayout.CENTER);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerMain());
    }
}
