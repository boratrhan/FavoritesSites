import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DisplayImage extends JFrame {
    private JTextField visitIDField;
    private JLabel imageLabel;

    public DisplayImage(String username) {
        setTitle("Display Location Image");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        visitIDField = new JTextField();
        JButton displayButton = new JButton("Display Image");
        JButton backButton = new JButton("Back"); // Geri butonu eklendi
        imageLabel = new JLabel("", SwingConstants.CENTER);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLocationImage();
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Enter Visit ID: "), BorderLayout.WEST);
        inputPanel.add(visitIDField, BorderLayout.CENTER);
        inputPanel.add(displayButton, BorderLayout.EAST);
        inputPanel.add(backButton, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
    }

    private void displayLocationImage() {
        String visitID = visitIDField.getText();
        try {
            int id = Integer.parseInt(visitID);
            String fileName = "Location" + id + ".jpg";

            File file = new File("C:/Users/90543/IdeaProjects/sqlaktarma/src/"+fileName);
            if (file.exists()) {
                ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                imageLabel.setIcon(imageIcon);
                imageLabel.setText("");
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("Image file not found for Visit ID: " + visitID);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Visit ID.");
        }
    }
}
