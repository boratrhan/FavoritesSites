import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DisplaySpringVisits extends JFrame {


    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favoritesites";
    static final String USER = "root";
    static final String PASS = "*****";

    public DisplaySpringVisits(String username) {
        setTitle("Spring Visits");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        JButton backButton = new JButton("Back");

        Set<String> springVisits = findSpringVisits();

        if (!springVisits.isEmpty()) {
            resultArea.setText("Countries visited only in spring:\n");
            for (String country : springVisits) {
                resultArea.append(country + "\n");
            }
        } else {
            resultArea.setText("No countries visited only in spring");
        }

        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

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


    private Set<String> findSpringVisits() {
        Set<String> springVisits = new HashSet<>();

        String query = "SELECT DISTINCT countryname FROM visits WHERE seasonvisited = 'Spring'";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String countryName = rs.getString("countryname");
                if (isVisitedOnlyInSpring(countryName)) {
                    springVisits.add(countryName);
                }
            }

            return springVisits;
        } catch (SQLException e) {
            e.printStackTrace();
            return springVisits;
        }
    }


    private boolean isVisitedOnlyInSpring(String countryName) {
        String query = "SELECT DISTINCT seasonvisited FROM visits WHERE countryname = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, countryName);
            ResultSet rs = pstmt.executeQuery();

            Set<String> seasonsVisited = new HashSet<>();
            while (rs.next()) {
                seasonsVisited.add(rs.getString("seasonvisited"));
            }
            return seasonsVisited.size() == 1 && seasonsVisited.contains("Spring");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
