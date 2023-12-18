import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;


public class Book extends JFrame {
    private JDateChooser dateChooser;
    private JTextField cusidf;
    private int carid;
    private JTextField pricefield;
    private JTextField extraKmField;
    private JTextField amountf;
    private JTextField cusf;
    private JTextField noofdaysf;
    private JTextField noofexkmf;
    private JLabel total;

    public Book(int carid) {

        this.carid=carid;

        System.out.println(carid);
        setVisible(true);

        setTitle("Booking and Pricing");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2));

        JPanel bookZone = createBookZone();
        mainPanel.add(bookZone);

        JPanel topZone = createTopZone();
        mainPanel.add(topZone);

        JPanel editZone = createEditZone();
        mainPanel.add(editZone);

        JPanel bottomZone = createBottomZone();
        mainPanel.add(bottomZone);

        setContentPane(mainPanel);
    }

    private JPanel createBookZone() {
        JPanel bookZone = new JPanel(new BorderLayout());
        JLabel noOfDaysLabel = new JLabel("Select Date:");
        noOfDaysLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dateChooser = new JDateChooser();
        JLabel cusidl = new JLabel("Customer ID:");
        cusidl.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel no = new JLabel("");
        cusidf = new JTextField(15);
        JButton bookButton = new JButton("Book");
        bookButton.addMouseListener(new HoverMouseListener());
        bookButton.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel h1 = new JLabel("Booking");
        JLabel n1 = new JLabel("");
        h1.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel topZone = new JPanel(new GridLayout(4, 2));
        topZone.add(h1);
        topZone.add(n1);
        topZone.add(noOfDaysLabel);
        topZone.add(dateChooser);
        topZone.add(cusidl);
        topZone.add(cusidf);
        topZone.add(no);
        topZone.add(bookButton);
        bookZone.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        topZone.setBackground(new Color(196, 164, 132));

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.Date selectedDateUtil = dateChooser.getDate();
                java.sql.Date selectedDateSql = new java.sql.Date(selectedDateUtil.getTime());
                bookcar(selectedDateSql);
            }
        });
        bookZone.add(topZone, BorderLayout.CENTER);

        return bookZone;
    }
    public void bookcar(Date date){
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO book (book_date, cusid, vehiid) VALUES ( ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(2, cusidf.getText());
                preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
                preparedStatement.setInt(3, carid);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Booked successfully");

                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Book");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private JPanel createTopZone() {
        JPanel pricezone = new JPanel(new BorderLayout());
        JLabel noofdaysl = new JLabel("Enter no.Of days:");
        noofdaysl.setFont(new Font("Arial", Font.BOLD, 18));
        noofdaysf = new JTextField(15);
        JLabel noofexkml = new JLabel("Enter no.Of Extra KM:");
        noofexkml.setFont(new Font("Arial", Font.BOLD, 18));
        noofexkmf = new JTextField(15);
        total = new JLabel("");
        total.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel no = new JLabel("");
        JLabel no1 = new JLabel("");
        JButton cal=new JButton("Calculate");
        cal.addMouseListener(new HoverMouseListener());
        cal.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel h1 = new JLabel("Pricing");
        JLabel n1 = new JLabel("");
        h1.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel  righttop= new JPanel(new GridLayout(5, 2));
        pricezone.setBorder(BorderFactory.createTitledBorder("pricing Zone"));
        righttop.add(h1);
        righttop.add(n1);
        righttop.add(noofdaysl);
        righttop.add(noofdaysf);
        righttop.add(noofexkml);
        righttop.add(noofexkmf);
        righttop.add(no);
        righttop.add(total);
        righttop.add(no1);
        righttop.add(cal);
        righttop.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        righttop.setBackground(Color.YELLOW);

        cal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

        return righttop;
    }

    public void calculate() {
        int price = 0;
        int additional_chargers = 0;
        int tot=0;
        int noofdays=0;
        int extra=0;


        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT charge,additional_chargers FROM vehicle WHERE vehicle_id = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, carid);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        price = resultSet.getInt("charge");
                        additional_chargers = resultSet.getInt("additional_chargers");
                        noofdays= Integer.parseInt(noofdaysf.getText());
                        extra= Integer.parseInt(noofexkmf.getText());
                        tot=(price*noofdays)+(extra*additional_chargers);
                        total.setText("Total- Rs."+String.valueOf(tot)+".00");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    private JPanel createEditZone() {
        JPanel editZone = new JPanel(new BorderLayout());
        JLabel pricelbl = new JLabel("Price:");
        pricelbl.setFont(new Font("Arial", Font.BOLD, 18));
        pricefield = new JTextField(15);
        JLabel extraKmLabel = new JLabel("Price per Extra KM:");
        extraKmLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel no = new JLabel("");
        extraKmField =new JTextField(15);
        JButton updateButton = new JButton("Update");
        updateButton.addMouseListener(new HoverMouseListener());
        updateButton.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel h1 = new JLabel("Update");
        JLabel n1 = new JLabel("");
        h1.setFont(new Font("Arial", Font.BOLD, 36));


        JPanel bottomZone = new JPanel(new GridLayout(4, 2));
        bottomZone.setBorder(BorderFactory.createTitledBorder("Book Zone"));
        bottomZone.add(h1);
        bottomZone.add(n1);
        bottomZone.add(pricelbl);
        bottomZone.add(pricefield);
        bottomZone.add(extraKmLabel);
        bottomZone.add(extraKmField);
        bottomZone.add(no);
        bottomZone.add(updateButton);
        bottomZone.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        bottomZone.setBackground(Color.YELLOW);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatecar();
            }
        });

        editZone.add(bottomZone, BorderLayout.CENTER);

        return editZone;
    }

    public void updatecar(){
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "UPDATE  vehicle SET charge=?, additional_chargers=? WHERE vehicle_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, pricefield.getText());
                preparedStatement.setString(2, extraKmField.getText());
                preparedStatement.setInt(3, carid);


                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Updated successfully");

                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Update");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    private JPanel createBottomZone() {
        JPanel bottomZone = new JPanel(new BorderLayout());
        JLabel amountl = new JLabel("Enter Amount:");
        amountl.setFont(new Font("Arial", Font.BOLD, 18));
        amountf = new JTextField(15);
        JLabel cusl = new JLabel("Customer ID:");
        cusl.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel no = new JLabel("");
        JLabel no1 = new JLabel("");
        cusf = new JTextField(15);
        JButton pay = new JButton("Pay");
        pay.addMouseListener(new HoverMouseListener());
        pay.setFont(new Font("Arial", Font.BOLD, 18));
        JButton back= new JButton("Back");
        back.addMouseListener(new HoverMouseListener());
        back.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel h1 = new JLabel("Payments");
        JLabel n1 = new JLabel("");
        h1.setFont(new Font("Arial", Font.BOLD, 36));


        JPanel bottomZone1 = new JPanel(new GridLayout(5, 2));
        bottomZone1.setBorder(BorderFactory.createTitledBorder("Book Zone"));
        bottomZone1.add(h1);
        bottomZone1.add(n1);
        bottomZone1.add(amountl);
        bottomZone1.add(amountf);
        bottomZone1.add(cusl);
        bottomZone1.add(cusf);
        bottomZone1.add(no);
        bottomZone1.add(pay);
        bottomZone1.add(no1);
        bottomZone1.add(back);
        bottomZone1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        bottomZone1.setBackground(new Color(196, 164, 132));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                Daily daily = new Daily();
                daily.setVisible(true);
            }
        });
        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate today = LocalDate.now();

                payment(Date.valueOf(today));
            }
        });

        return bottomZone1;
    }
    public void payment(Date date){
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO payment (amount, date, cusid) VALUES ( ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, amountf.getText());
                preparedStatement.setDate(2, new java.sql.Date(date.getTime()));
                preparedStatement.setInt(3, Integer.parseInt(cusf.getText()));


                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "saved successfully");

                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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

    private void goBack() {

        JOptionPane.showMessageDialog(this, "Back Button Clicked");
    }


    public static void main(String[] args) {


    }
}
