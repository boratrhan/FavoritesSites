import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddLoc extends JFrame {
    private JTextField CountryName, CityName, BestFeature, Comments;
    JRadioButton Winter, Spring, Summer, Autumn;
    private JComboBox<Integer> Year, Rating;
    private JButton submit,back;
    private String selectedSeason;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favoritesites";
    static final String USER = "root";
    static final String PASS = "*****";
    private String name;

    AddLoc(String username) {
        this.name = username;

        setTitle("Main Frame");
        setSize(500, 350);
        setLayout(new GridLayout(11, 2, 5, 5));
        Year = new JComboBox<>();

        for (int i = 1900; i <= 2024; i++) {
            Year.addItem(i);
        }
        Rating = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            Rating.addItem(i);
        }

        CountryName = new JTextField();
        CityName = new JTextField();
        BestFeature = new JTextField();
        Comments = new JTextField();

        ButtonGroup b = new ButtonGroup();
        Winter = new JRadioButton("Winter");
        Spring = new JRadioButton("Spring");
        Summer = new JRadioButton("Summer");
        Autumn = new JRadioButton("Autumn");
        submit = new JButton("Submit");
        back = new JButton("Back");

        b.add(Winter);
        b.add(Spring);
        b.add(Summer);
        b.add(Autumn);

        add(new JLabel("Country Name:"));
        add(CountryName);
        add(new JLabel("City Name:"));
        add(CityName);
        add(new JLabel("Year Visited:"));
        add(Year);

        add(new JLabel("Season:"));
        add(new JLabel(""));
        add(Winter);
        add(Spring);

        add(Summer);
        add(Autumn);
        add(new JLabel("Best Feature:"));
        add(BestFeature);
        add(new JLabel("Comments"));
        add(Comments);
        add(new JLabel("Rating"));
        add(Rating);
        add(submit);
        add(back);

        Winter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Winter";
            }
        });

        Spring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Spring";
            }
        });

        Summer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Summer";
            }
        });

        Autumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeason = "Autumn";
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

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String countryname = CountryName.getText();
                String cityname = CityName.getText();
                String bestfeature = BestFeature.getText();
                String comment = Comments.getText();
                String selectedseason = selectedSeason;
                int yearvisited = (int) Year.getSelectedItem();
                int rating = (int) Rating.getSelectedItem();

                saveToDatabase(countryname, cityname, selectedseason, bestfeature, comment, rating, yearvisited, username);
            }
        });
    }

    private void saveToDatabase(String countryname, String cityname, String selectedseason, String bestfeature, String comment, int rating, int yearvisited, String username) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO visits (countryname, cityname, seasonvisited, bestfeature, comment, rating, yearvisited, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, countryname);
            preparedStatement.setString(2, cityname);
            preparedStatement.setString(3, selectedseason);
            preparedStatement.setString(4, bestfeature);
            preparedStatement.setString(5, comment);
            preparedStatement.setInt(6, rating);
            preparedStatement.setInt(7, yearvisited);
            preparedStatement.setString(8, username);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "The user was successfully registered.");
        } catch (SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred during saving to the database.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error has occurred.");
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
