package com.demo1.view;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.demo1.firebaseConfig.DataService;
import com.demo1.navigation.Navigation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class SignUp {

    private Navigation nav;
    private Group group;
    private TextField emailTextField;
    private PasswordField psTextField;
    private ImageView profilePicView;
    private String profilePicPath; // Variable to store profile picture path

    // Constructor
    public SignUp(Navigation nav) {
        this.nav = nav;
        initialize();
    }

    // initialize layout and components
    private void initialize() {
        // Load the video
        String videoPath = getClass().getResource("/images/videoplayback.mp4").toExternalForm();
        Media videoMedia = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(videoMedia);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        // Set video properties
        mediaView.setFitWidth(1000);
        mediaView.setFitHeight(1000);
        mediaView.setPreserveRatio(true);

        // Play the video in a loop
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        // Signup Label
        Label login = new Label("S I G N U P");
        login.setFont(Font.font("Verdana", 45));
        login.setStyle("-fx-text-fill: #EEEEEE");

        // Divider Line
        Line line = new Line();
        line.setStartX(500);
        line.setEndX(900);
        line.setStroke(Color.web("#EEEEEE"));

        // Copyright Label
        Label copyright = new Label("©Prohealth");
        copyright.setPadding(new Insets(190, 0, 0, 0));
        copyright.setStyle("-fx-text-fill: #EEEEEE");

        // Email Label and TextField
        Label email = new Label("Enter Username");
        email.setFont(Font.font("Poppins", 19));
        email.setOpacity(0.5);
        email.setStyle("-fx-text-fill: #EEEEEE");
        email.setPadding(new Insets(5));
        emailTextField = new TextField();
        emailTextField.setPromptText("Enter Username");
        emailTextField.getStyleClass().add("rounded-text-field");

        VBox vbEmail = new VBox(email, emailTextField);
        vbEmail.setPadding(new Insets(30, 0, 10, 0));

        // Password Label and TextField
        Label password = new Label("Enter Password");
        password.setFont(Font.font("Poppins", 19));
        password.setOpacity(0.5);
        password.setStyle("-fx-text-fill: #EEEEEE");
        password.setPadding(new Insets(5));
        psTextField = new PasswordField();
        psTextField.setPromptText("Enter Password");
        psTextField.getStyleClass().add("rounded-text-field");

        VBox vbPassword = new VBox(password, psTextField);
        vbPassword.setPadding(new Insets(10, 0, 50, 0));

        // Load the default profile picture
        Image defaultProfilePic = new Image(getClass().getResourceAsStream("/images/user.png"));
        profilePicView = new ImageView(defaultProfilePic);

        // Optionally set the dimensions of the ImageView
        profilePicView.setFitWidth(100);
        profilePicView.setFitHeight(100);
        profilePicView.setPreserveRatio(true);

        // Button to edit profile picture
        Button editPicButton = new Button("Edit");
        editPicButton.setStyle(
                "-fx-background-color: #18610fda; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px; -fx-background-radius: 5px;");
        editPicButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose Profile Picture");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    profilePicPath = selectedFile.toURI().toString(); // Store the path
                    Image userImage = new Image(profilePicPath);
                    profilePicView.setImage(userImage);
                }
            }
        });

        // HBox for profile picture and edit button
        HBox hbProfilePic = new HBox(10, profilePicView, editPicButton);
        hbProfilePic.setPadding(new Insets(20, 0, 0, 0));

        // VBox layout for email, password, profile picture, and button
        VBox vbUserDetails = new VBox(vbEmail, vbPassword);
        vbUserDetails.setPadding(new Insets(0, 0, 50, 0));

        // Signup Button
        Button signupButton = new Button("Signup & Login");
        signupButton.getStyleClass().add("rounded-loginn-button");
        signupButton.setPadding(new Insets(10, 50, 10, 50));

        // navigate to home page (for time being navigating to que1)
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSignup(emailTextField.getText(), psTextField.getText()); // Handle signup
            }
        });

        // Social Media Logos
        Image social1 = new Image("/images/facebook.png");
        ImageView s1 = new ImageView(social1);
        s1.setFitHeight(25);
        s1.setFitWidth(25);
        s1.setPreserveRatio(true);

        Image social2 = new Image("/images/instagram.png");
        ImageView s2 = new ImageView(social2);
        s2.setFitHeight(25);
        s2.setFitWidth(25);
        s2.setPreserveRatio(true);

        Image social3 = new Image("/images/twitter.png");
        ImageView s3 = new ImageView(social3);
        s3.setFitHeight(25);
        s3.setFitWidth(25);
        s3.setPreserveRatio(true);

        // Add logos with spacing in an HBox
        HBox logos = new HBox(20); // 20 pixels of spacing between logos
        logos.getChildren().addAll(s1, s2, s3);
        logos.setPadding(new Insets(50, 0, 0, 0)); // Vertical gap above the icons

        // VBox layout for login components
        VBox vb = new VBox(login, line, vbUserDetails, signupButton, logos, copyright);
        // vb.setPadding(new Insets(150, 300, 150, 0));
        vb.setPadding(new Insets(100, 300, 150, 100));

        // Combine the MediaView and VBox in an HBox
        HBox flc = new HBox(mediaView, vb);
        flc.setMinHeight(1000);
        flc.setMinWidth(1500);
        flc.setStyle("-fx-background-color: #222831");

        group = new Group(flc);
    }

    // Method to handle signup action
    private void handleSignup(String username, String password) {
        DataService dataService; // Local instance of DataService
        try {
            dataService = new DataService(); // Initialize DataService instance
            // Create a map to hold user data
            Map<String, Object> data = new HashMap<>();
            data.put("password", password);
            data.put("username", username);
            data.put("profilePicPath", profilePicPath); // Add profile picture path to user data
            // Add user data to Firestore
            dataService.addData("users", username, data);
            Login.loggedinUserName = username;
            System.out.println("username : " + Login.loggedinUserName);
            System.out.println("User registered successfully");
            // directly signup and login and go to questionnair
            nav.navigateToQue1();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // method to get group
    public Group getGroup() {
        return group;
    }

    // get value from emailTextField
    public String getEmailTextFieldValue() {
        return emailTextField.getText();
    }

    // set value of emailTextField
    public void setEmailTextFieldValue(String value) {
        emailTextField.setText(value);
    }

    // get value from psTextField
    public String getpsTextFieldValue() {
        return psTextField.getText();
    }

    // set value of psTextField
    public void setpsTextFieldValue(String value) {
        psTextField.setText(value);
    }

}
