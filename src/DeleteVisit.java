import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteVisit extends JFrame {
    private JTextField visitIDField;
    private JButton deleteButton, back;

    public DeleteVisit(String username) {
        setTitle("Delete Visit");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 5, 5));

        visitIDField = new JTextField();
        deleteButton = new JButton("Delete");
        back = new JButton("Back");

        add(new JLabel("Visit ID:"));
        add(visitIDField);
        add(new JLabel(""));
        add(deleteButton);
        add(new JLabel(""));
        add(back);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteVisit();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transaction transaction = new Transaction(username);
                transaction.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                transaction.setVisible(true);
                dispose();
            }
        });
    }

    private void deleteVisit() {
        int visitID = Integer.parseInt(visitIDField.getText());

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/favoritesites", "root", "*****");

            String deleteVisitSQL = "DELETE FROM visits WHERE visitid=?";
            preparedStatement = conn.prepareStatement(deleteVisitSQL);
            preparedStatement.setInt(1, visitID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Visit with ID " + visitID + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Visit with ID " + visitID + " not found.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred.");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
