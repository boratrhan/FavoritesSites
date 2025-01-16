import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SharedVisits extends JFrame {
    private JTextArea visitInfoArea;

    public SharedVisits(String username) {
        setTitle("Shared Visits");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        visitInfoArea = new JTextArea();
        visitInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(visitInfoArea);

        add(scrollPane, BorderLayout.CENTER);

        displaySharedVisits(username);
    }

    private void displaySharedVisits(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/favoritesites", "root", "*****");

            String query = "SELECT * FROM sharedvisits WHERE friendsname = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            sb.append("Shared Visits:\n\n");
            while (rs.next()) {
                int visitId = rs.getInt("visitid");
                String visitInfo = getVisitInfo(visitId);
                if (visitInfo != null) {
                    sb.append(visitInfo).append("\n");
                }
            }
            visitInfoArea.setText(sb.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while fetching shared visits.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private String getVisitInfo(int visitId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/favoritesites", "root", "*****");

            String query = "SELECT * FROM visits WHERE visitid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, visitId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String countryName = rs.getString("countryname");
                String cityName = rs.getString("cityname");
                String seasonVisited = rs.getString("seasonvisited");
                String bestFeature = rs.getString("bestfeature");
                return "Visit ID: " + visitId + ", Country: " + countryName + ", City: " + cityName + ", Season: " + seasonVisited + ", Best Feature: " + bestFeature;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return null;
    }
}
