import javax.swing.*;
import java.awt.*;

public class Transaction extends JFrame {
    private JButton addLocationButton;
    private JButton shareWithFriendButton;
    private JButton displayEditUpdateButton;
    private JButton displayImageButton;
    private JButton displayFoodButton;
    private JButton displayVisitsButton;
    private JButton displayVisitStatisticsButton;
    private JButton displaySpringVisitsButton;
    private JButton deleteVisitButton;
    private JButton displaySharedVisitsButton;

    public Transaction(String username) {
        setTitle("Transaction Screen");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1, 10, 10));

        addLocationButton = new JButton("Add Location");
        shareWithFriendButton = new JButton("Share with Friend");
        displayEditUpdateButton = new JButton("Display, Edit, Update");
        displayImageButton = new JButton("Display Location Image");
        displayFoodButton = new JButton("Display Food Countries");
        displayVisitsButton = new JButton("Display Visits by Year");
        displayVisitStatisticsButton = new JButton("Display Visit Most");
        displaySpringVisitsButton = new JButton("Display Spring Visits");
        deleteVisitButton = new JButton("Delete Visit");
        displaySharedVisitsButton = new JButton("Display Shared Visits");

        add(addLocationButton);
        add(shareWithFriendButton);
        add(displayEditUpdateButton);
        add(displayImageButton);
        add(displayFoodButton);
        add(displayVisitsButton);
        add(displayVisitStatisticsButton);
        add(displaySpringVisitsButton);
        add(deleteVisitButton);
        add(displaySharedVisitsButton);

        addLocationButton.addActionListener(e -> openAddlocForm(username));
        shareWithFriendButton.addActionListener(e -> openShareWithFriendForm(username));
        displayEditUpdateButton.addActionListener(e -> openDisplayEditUpdateForm(username));
        displayImageButton.addActionListener(e -> openDisplayImageForm(username));
        displayFoodButton.addActionListener(e -> openDisplayFoodForm());
        displayVisitsButton.addActionListener(e -> openDisplayVisitsForm(username));
        displayVisitStatisticsButton.addActionListener(e -> openDisplayVisitStatisticsForm(username));
        displaySpringVisitsButton.addActionListener(e -> openDisplaySpringVisitsForm(username));
        deleteVisitButton.addActionListener(e -> openDeleteVisitForm(username));
        displaySharedVisitsButton.addActionListener(e -> openSharedVisitsForm(username));
    }

    private void openAddlocForm(String name) {
        AddLoc add= new AddLoc(name);
        add.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add.setVisible(true);
        dispose();
    }

    private void openShareWithFriendForm(String username) {
        ShareWithFriend shareWithFriend = new ShareWithFriend(username);
        shareWithFriend.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shareWithFriend.setVisible(true);
        dispose();
    }

    private void openDisplayEditUpdateForm(String username) {
        EditVisit displayEditUpdateFrame = new EditVisit(username);
        displayEditUpdateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayEditUpdateFrame.setVisible(true);
        dispose();
    }

    private void openDisplayImageForm(String username) {
        DisplayImage displayImageFrame = new DisplayImage(username);
        displayImageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayImageFrame.setVisible(true);
    }

    private void openDisplayFoodForm() {
        Food foodFrame = new Food();
        foodFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        foodFrame.setVisible(true);
    }

    private void openDisplayVisitsForm(String username) {
        DisplayVisits displayVisitsFrame = new DisplayVisits(username);
        displayVisitsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayVisitsFrame.setVisible(true);
    }

    private void openDisplayVisitStatisticsForm(String username) {
        DisplayVisitMost visitStatisticsFrame = new DisplayVisitMost(username);
        visitStatisticsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        visitStatisticsFrame.setVisible(true);
    }

    private void openDisplaySpringVisitsForm(String username) {
        DisplaySpringVisits displaySpringVisitsFrame = new DisplaySpringVisits(username);
        displaySpringVisitsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displaySpringVisitsFrame.setVisible(true);
    }

    private void openDeleteVisitForm(String username) {
        DeleteVisit deleteVisitFrame = new DeleteVisit(username);
        deleteVisitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteVisitFrame.setVisible(true);
    }

    private void openSharedVisitsForm(String username) {
        SharedVisits displaySharedVisitsFrame = new SharedVisits(username);
        displaySharedVisitsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displaySharedVisitsFrame.setVisible(true);
    }
}
