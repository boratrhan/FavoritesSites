import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DisplayVisitMost extends JFrame {
    private String username;


    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favoritesites";
    static final String USER = "root";
    static final String PASS = "*****";

    public DisplayVisitMost(String username) {
        this.username = username;

        setTitle("Visit Statistics");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        String mostVisitedCountry = findMostVisitedCountry(username);

        if (mostVisitedCountry != null) {
            resultArea.setText("Most visited country by " + username + ": " + mostVisitedCountry);
        } else {
            resultArea.setText("No visits found for " + username);
        }

        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        addBackButton();
    }

    private String findMostVisitedCountry(String username) {
        Map<String, Integer> countryCounts = new HashMap<>();

        String query = "SELECT countryname FROM visits WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String countryName = rs.getString("countryname");
                countryCounts.put(countryName, countryCounts.getOrDefault(countryName, 0) + 1);
            }

            int maxCount = 0;
            String mostVisitedCountry = null;
            for (Map.Entry<String, Integer> entry : countryCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostVisitedCountry = entry.getKey();
                }
            }

            return mostVisitedCountry;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void addBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);
    }
}
