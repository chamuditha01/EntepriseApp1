import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Payment extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public Payment() {
        setLayout(new BorderLayout());
        setTitle("Payment Table ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{"Id", "Amount", "Payment Date","cusid"});
        fetchDataFromDatabase();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton BackButton = new JButton("Back");
        BackButton.addMouseListener(new HoverMouseListener());
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                Dashboard dash = new Dashboard();
                dash.setVisible(true);
            }
        });
        getContentPane().add(BackButton, BorderLayout.EAST);
    }

    private void fetchDataFromDatabase() {


        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT id, amount, date, cusid FROM payment";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getInt("Id"),
                            resultSet.getString("Amount"),
                            resultSet.getString("date"),
                            resultSet.getString("cusid"),
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        SwingUtilities.invokeLater(() -> new Payment().setVisible(true));
    }
}
