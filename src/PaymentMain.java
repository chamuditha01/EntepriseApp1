import javax.swing.*;
import java.awt.*;

public class PaymentMain extends JFrame {
    private JPanel navbarPanel;


    public PaymentMain() throws HeadlessException {
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
        tableTitle.setText("Payment Table");
        tableTitle.setBackground(new Color(196, 164, 132));
        tableTitle.setOpaque(true);
        tableTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        tableTitle.setHorizontalAlignment(SwingConstants.CENTER);
        subscreen.add(tableTitle, BorderLayout.NORTH);

        Payment databaseTableExample = new Payment();
        subscreen.add(databaseTableExample.getContentPane(), BorderLayout.CENTER);
        container.add(subscreen, BorderLayout.CENTER);

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentMain());
    }
}
