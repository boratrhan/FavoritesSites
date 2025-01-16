import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Food extends JFrame {
    private JTextArea resultArea;

    public Food() {
        setTitle("Food Countries");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(scrollPane, BorderLayout.CENTER);

        displayFoodCountries();
    }

    private void displayFoodCountries() {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/favoritesites", "root", "*****");
            statement = conn.createStatement();

            String sql = "SELECT countryname FROM visits WHERE LOWER(bestfeature) = 'food' ORDER BY rating DESC";
            resultSet = statement.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            sb.append("Food Countries (Sorted by Rating):\n\n");
            while (resultSet.next()) {
                String countryName = resultSet.getString("countryname");
                sb.append(countryName).append("\n");
            }
            resultArea.setText(sb.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while fetching data.");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
