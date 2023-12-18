import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Daily extends JFrame {
    private JTable availabilityTable;
    private DefaultTableModel availabilityTableModel;
    private JDateChooser dateChooser;


    public Daily() {

            JFrame frame = new JFrame("Subscreen Example");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("Daily Rentals");
            titleLabel.setBackground(new Color(196, 164, 132));
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 36));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);


            JPanel subscreenPanel = new JPanel();
            subscreenPanel.setLayout(new GridLayout(1, 3));


            JPanel column1 = createColoredPanel(Color.YELLOW);
            JPanel column2 = createColoredPanel(Color.YELLOW);
            JPanel column3 = createColoredPanel(Color.YELLOW);


            addComponentsToColumn(column1, "C:/Users/ASUS/Desktop/EAD Cw1/src/Suzuki-Alto-Price-in-Sri-Lanka.jpg", "ALTO");
            addComponentsToColumn(column2, "C:/Users/ASUS/Desktop/EAD Cw1/src/Toyota-Aqua-Price-in-Sri-Lanka.jpg", "AQUA");
            addComponentsToColumn(column3, "C:/Users/ASUS/Desktop/EAD Cw1/src/Toyota-Axio-Price-in-Sri-Lanka.jpg", "AXIO");

            subscreenPanel.add(column1);
            subscreenPanel.add(column2);
            subscreenPanel.add(column3);

            JButton button1 = new JButton("View Availability");
            button1.addMouseListener(new HoverMouseListener());
            JButton button2 = new JButton("Book");
            button2.addMouseListener(new HoverMouseListener());
            JButton button3 = new JButton("View Availability");
            button3.addMouseListener(new HoverMouseListener());
            JButton button4 = new JButton("Book");
            button4.addMouseListener(new HoverMouseListener());
            JButton button5 = new JButton("View Availability");
            button5.addMouseListener(new HoverMouseListener());
            JButton button6 = new JButton("Book");
            button6.addMouseListener(new HoverMouseListener());
            dateChooser = new JDateChooser();

            column1.add(button1);
            column1.add(button2);

            column2.add(button3);
            column2.add(button4);
            column2.add(dateChooser);

            column3.add(button5);
            column3.add(button6);

            mainPanel.add(titleLabel, BorderLayout.NORTH);
            mainPanel.add(subscreenPanel, BorderLayout.CENTER);
            JPanel bottomPanel = createColoredPanel(Color.YELLOW);
            JPanel leftPanel = createColoredPanel(Color.YELLOW);
            JPanel rightPanel = createColoredPanel(Color.YELLOW);
            JButton backButton = new JButton("Back");
            rightPanel.add(backButton);
            backButton.addMouseListener(new HoverMouseListener());
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    back(e);
                }
            });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date selectedDate = dateChooser.getDate();

                if (selectedDate != null) {
                    updateAvailabilityTableData(1, selectedDate);
                    bottomPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Daily.this, "Please select a date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                Book book = new Book(1);
                book.setVisible(true);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date selectedDate = dateChooser.getDate();

                if (selectedDate != null) {
                    updateAvailabilityTableData(2, selectedDate);
                    bottomPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Daily.this, "Please select a date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                Book book = new Book(2);
                book.setVisible(true);
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date selectedDate = dateChooser.getDate();

                if (selectedDate != null) {
                    updateAvailabilityTableData(3, selectedDate);
                    bottomPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Daily.this, "Please select a date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                Book book = new Book(3);
                book.setVisible(true);
            }
        });


        bottomPanel.setPreferredSize(new Dimension(mainPanel.getWidth(), 100));
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);
            mainPanel.add(leftPanel, BorderLayout.WEST);
            mainPanel.add(rightPanel, BorderLayout.EAST);

        availabilityTableModel = new DefaultTableModel();
        availabilityTableModel.setColumnIdentifiers(new Object[]{"booked date","customerId"});
        availabilityTable = new JTable(availabilityTableModel);
        JScrollPane availabilityScrollPane = new JScrollPane(availabilityTable);
        bottomPanel.add(availabilityScrollPane, BorderLayout.CENTER);



        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        bottomPanel.setVisible(false);
    }

    private static void addComponentsToColumn(JPanel columnPanel, String imagePath, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);


        label.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        columnPanel.add(label);

        ImageIcon imageIcon = new ImageIcon(imagePath);

        if (imageIcon.getIconWidth() > 0 && imageIcon.getIconHeight() > 0) {
            JLabel imageLabel = new JLabel();

            int newWidth = imageIcon.getIconWidth() / 5;
            int newHeight = imageIcon.getIconHeight() /5;

            Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

            imageLabel.setIcon(scaledImageIcon);
            columnPanel.add(imageLabel);
        } else {
            System.err.println("Invalid image dimensions for file: " + imagePath);
        }

        double price = fetchPriceFromDatabase(labelText);
        JLabel priceLabel = new JLabel("Price: RS." + price + "0");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        columnPanel.add(priceLabel);
    }

    private static double fetchPriceFromDatabase(String carName) {
        double price = 0.0;


        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT charge FROM vehicle WHERE brand = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, carName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        price = resultSet.getDouble("charge");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    private void back(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        frame.dispose();

        Dashboard addEmployeeForm = new Dashboard();
        addEmployeeForm.setVisible(true);
    }

    private void updateAvailabilityTableData(int carname, Date date) {
        availabilityTableModel.setRowCount(0);

        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT book_date, cusid FROM book WHERE cusid=? AND book_date=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, carname);
                preparedStatement.setDate(2, new java.sql.Date(date.getTime()));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        availabilityTableModel.addRow(new Object[]{
                                resultSet.getString("book_date"),
                                resultSet.getString("cusid"),
                        });
                    }
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

    private static JPanel createColoredPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Daily());
    }
}
