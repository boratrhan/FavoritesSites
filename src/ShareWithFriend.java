import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ShareWithFriend extends JFrame {
    private JTextField friendUsernameField;
    private JTextField visitIDField;
    private JTextArea visitInfoArea;
    private JButton shareButton, backButton;
    private String username;

    public ShareWithFriend(String username) {
        this.username = username;

        setTitle("Share with Friend");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        friendUsernameField = new JTextField();
        visitIDField = new JTextField();
        visitInfoArea = new JTextArea();
        shareButton = new JButton("Share");
        backButton = new JButton("Back");

        add(new JLabel("Friend's Username:"));
        add(friendUsernameField);
        add(new JLabel("Visit ID:"));
        add(visitIDField);
        add(new JLabel(""));
        add(shareButton);
        add(new JLabel(""));
        add(backButton);

        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String friendUsername = friendUsernameField.getText();
                int visitID = Integer.parseInt(visitIDField.getText());
                String visitInfo = visitInfoArea.getText();

                shareVisitWithFriend(friendUsername, visitID);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transaction transaction = new Transaction(username);
                transaction.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                transaction.setVisible(true);
                dispose();
            }
        });
    }

    private void shareVisitWithFriend(String friendsname, int visitid) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/favoritesites", "root", "*****");

            String getVisitSQL = "SELECT * FROM visits WHERE visitid=?";
            preparedStatement = conn.prepareStatement(getVisitSQL);
            preparedStatement.setInt(1, visitid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String countryName = resultSet.getString("countryname");
                String cityName = resultSet.getString("cityname");
                String seasonVisited = resultSet.getString("seasonvisited");
                String bestFeature = resultSet.getString("bestfeature");
                String comment = resultSet.getString("comment");
                int rating = resultSet.getInt("rating");
                int yearVisited = resultSet.getInt("yearvisited");
                String insertSharedVisitSQL = "INSERT INTO sharedvisits (username, friendsname, visitid) VALUES (?, ?, ?)";
                preparedStatement = conn.prepareStatement(insertSharedVisitSQL);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, friendsname);
                preparedStatement.setInt(3, visitid);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Visit shared with " + friendsname);
            } else {
                JOptionPane.showMessageDialog(this, "Visit with ID " + visitid + " not found.");
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
