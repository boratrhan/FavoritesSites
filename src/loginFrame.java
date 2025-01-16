import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/favoritesites";
    static final String USER = "root";
    static final String PASS = "*****";

    public loginFrame() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 5, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
    }

    private void login() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT * FROM userinfo WHERE username=? AND password=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, enteredUsername);
            preparedStatement.setString(2, enteredPassword);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                makeTransactions(enteredUsername);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }

        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred.");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private void makeTransactions(String username) {
        Transaction transactionScreen = new Transaction(username);
        transactionScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transactionScreen.setVisible(true);
        dispose();
    }
    private void register() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO userinfo (username, password) VALUES (?, ?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, enteredUsername);
            preparedStatement.setString(2, enteredPassword);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User successfully registered.");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }

        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
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
