import javax.swing.*;
import java.awt.*;

public class Navbar extends JPanel {

    private JLabel topicLabel;


    public Navbar() {
        setLayout(new FlowLayout());
        this.topicLabel = new JLabel();

        initializeUI();
    }

    private void initializeUI() {
        topicLabel.setText("On The Go Car Rental System");
        topicLabel.setBackground(new Color(135,45,78));
        topicLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        setBackground(new Color(57,107,116));
        add(topicLabel);

    }
}