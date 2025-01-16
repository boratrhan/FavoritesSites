import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DisplayVisits extends JFrame {
    private JTextField yearField;
    private JButton showButton, backButton;
    private JTextArea resultArea;
    private String username;

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/favoritesites";
    static final String USER = "root";
    static final String PASS = "*****";

    public DisplayVisits(String username) {
        this.username = username;

        setTitle("Display Visits by Year");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        yearField = new JTextField(10);
        showButton = new JButton("Show Visits");
        backButton = new JButton("Back");

        inputPanel.add(new JLabel("Enter year:"));
        inputPanel.add(yearField);
        inputPanel.add(showButton);
        inputPanel.add(backButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String year = yearField.getText();
                List<Visit> visits = getVisitsByYear(year, username);
                StringBuilder results = new StringBuilder();

                for (Visit visit : visits) {
                    results.append(visit.toString()).append("\n");
                }

                resultArea.setText(results.toString());
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

    private List<Visit> getVisitsByYear(String year, String username) {
        List<Visit> visits = new ArrayList<>();
        String query = "SELECT visitid, countryname, cityname, seasonvisited, bestfeature, comment, rating, yearvisited, username FROM visits WHERE yearvisited = ? AND username = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, year);
            pstmt.setString(2, username);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                int visitId = rs.getInt("visitid");
                String countryName = rs.getString("countryname");
                String cityName = rs.getString("cityname");
                String seasonVisited = rs.getString("seasonvisited");
                String bestFeature = rs.getString("bestfeature");
                String comment = rs.getString("comment");
                int rating = rs.getInt("rating");
                int yearVisited = rs.getInt("yearvisited");
                String dbUsername = rs.getString("username");
                visits.add(new Visit(visitId, countryName, cityName, seasonVisited, bestFeature, comment, rating, yearVisited, dbUsername));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while fetching visits.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return visits;
    }

    static class Visit {
        private int visitId;
        private String countryName;
        private String cityName;
        private String seasonVisited;
        private String bestFeature;
        private String comment;
        private int rating;
        private int yearVisited;
        private String username;

        public Visit(int visitId, String countryName, String cityName, String seasonVisited, String bestFeature, String comment, int rating, int yearVisited, String username) {
            this.visitId = visitId;
            this.countryName = countryName;
            this.cityName = cityName;
            this.seasonVisited = seasonVisited;
            this.bestFeature = bestFeature;
            this.comment = comment;
            this.rating = rating;
            this.yearVisited = yearVisited;
            this.username = username;
        }

        @Override
        public String toString() {
            return "VisitID: " + visitId +
                    ", Country: " + countryName +
                    ", City: " + cityName +
                    ", Season: " + seasonVisited +
                    ", Best Feature: " + bestFeature +
                    ", Comment: " + comment +
                    ", Rating: " + rating +
                    ", Year: " + yearVisited +
                    ", Username: " + username;
        }
    }
}