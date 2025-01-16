import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditVisit extends JFrame {
    private JComboBox<String> dataOptions;
    private JTextField visitIDField;
    private JTextField newValueField;
    private JButton updateButton,back;
    private String username;

    public EditVisit(String username) {
        this.username = username;
        setTitle("Edit");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));


        dataOptions = new JComboBox<>();
        dataOptions.addItem("Country Name");
        dataOptions.addItem("City Name");
        dataOptions.addItem("Season Visited");
        dataOptions.addItem("Best Feature");
        dataOptions.addItem("Comment");
        dataOptions.addItem("Rating");
        dataOptions.addItem("Year Visited");

        visitIDField = new JTextField();
        newValueField = new JTextField();
        updateButton = new JButton("Update");
        back = new JButton("Back");

        add(new JLabel("Select Data to Update:"));
        add(dataOptions);
        add(new JLabel("Visit ID:"));
        add(visitIDField);
        add(new JLabel("New Value:"));
        add(newValueField);
        add(new JLabel(""));
        add(updateButton);
        add(back);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                updateVisitData();
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

    private void updateVisitData() {
        String selectedData = (String) dataOptions.getSelectedItem();
        int visitID = Integer.parseInt(visitIDField.getText());
        String newValue = newValueField.getText();

        String columnName = null;
        switch (selectedData) {
            case "Country Name":
                columnName = "countryname";
                break;
            case "City Name":
                columnName = "cityname";
                break;
            case "Season Visited":
                columnName = "seasonvisited";
                break;
            case "Best Feature":
                columnName = "bestfeature";
                break;
            case "Comment":
                columnName = "comment";
                break;
            case "Rating":
                columnName = "rating";
                break;
            case "Year Visited":
                columnName = "yearvisited";
                break;
        }

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/favoritesites", "root", "*****");

            String checkOwnershipSQL = "SELECT * FROM visits WHERE visitid = ? AND username = ?";
            preparedStatement = conn.prepareStatement(checkOwnershipSQL);
            preparedStatement.setInt(1, visitID);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String updateSQL = "UPDATE visits SET " + columnName + " = ? WHERE visitid = ?";
                preparedStatement = conn.prepareStatement(updateSQL);
                preparedStatement.setString(1, newValue);
                preparedStatement.setInt(2, visitID);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Visit data updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid visit ID or the visit does not belong to you.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while updating visit data.");
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
