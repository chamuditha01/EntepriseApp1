import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Customer extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField amountField, dateField, cusIdField;

    public Customer() {
        setLayout(new BorderLayout());
        setTitle("Customer Table ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Id", "Name", "Email", "Tel"});
        fetchDataFromDatabase();

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel amountLabel = new JLabel("Name:");
        amountField = new JTextField(10);
        JLabel dateLabel = new JLabel("Address:");
        dateField = new JTextField(10);
        JLabel cusIdLabel = new JLabel("Tel");
        cusIdField = new JTextField(10);

        JButton submitButton = new JButton("Add");
        submitButton.addMouseListener(new HoverMouseListener());
        JButton viewbtn = new JButton("Search");
        viewbtn.addMouseListener(new HoverMouseListener());
        JButton refrshbtn = new JButton("Refresh");
        refrshbtn.addMouseListener(new HoverMouseListener());
        JButton delbtn = new JButton("Delete");
        delbtn.addMouseListener(new HoverMouseListener());
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        viewbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        refrshbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchDataFromDatabase();
            }
        });
        delbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                del();
            }
        });

        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(cusIdLabel);
        inputPanel.add(cusIdField);
        inputPanel.add(submitButton);
        inputPanel.add(viewbtn);
        inputPanel.add(refrshbtn);
        inputPanel.add(delbtn);

        add(inputPanel, BorderLayout.NORTH);

        JButton BackButton = new JButton("Back");
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                Dashboard dash = new Dashboard();
                dash.setVisible(true);
            }
        });

        getContentPane().add(BackButton, BorderLayout.SOUTH);
    }

    private void search() {
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT id, name, address, tel FROM customers WHERE tel=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                model.setRowCount(0);
                preparedStatement.setString(1, cusIdField.getText());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        model.addRow(new Object[]{
                                resultSet.getInt("Id"),
                                resultSet.getString("Name"),
                                resultSet.getString("Address"),
                                resultSet.getString("Tel"),
                        });
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void del() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int customerId = (int) table.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection connection = ConnectionManager.getConnection()) {
                String deleteQuery = "DELETE FROM customers WHERE id=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, customerId);
                    preparedStatement.executeUpdate();
                }
                model.removeRow(selectedRow);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void fetchDataFromDatabase() {
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT id, name, address, tel FROM customers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);

                 ResultSet resultSet = preparedStatement.executeQuery()) {
                model.setRowCount(0);
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getInt("Id"),
                            resultSet.getString("Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Tel"),
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitForm() {

        String name = amountField.getText();
        String address = dateField.getText();
        String tel = cusIdField.getText();


        try (Connection connection = ConnectionManager.getConnection()) {
            String insertQuery = "INSERT INTO customers (name, address, tel) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, tel);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        amountField.setText("");
        dateField.setText("");
        cusIdField.setText("");


        model.setRowCount(0);
        fetchDataFromDatabase();
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
        SwingUtilities.invokeLater(() -> new Customer().setVisible(true));
    }
}
