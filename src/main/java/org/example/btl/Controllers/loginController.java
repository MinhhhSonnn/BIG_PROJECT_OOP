package org.example.btl.Controllers;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.btl.Database.database;


public class loginController {

  @FXML
  private Label forgotPasswordLabel;

  @FXML
  private Button loginButton;

  @FXML
  private Button loginViewButton;

  @FXML
  private PasswordField passwordPasswordField;

  @FXML
  private Button signUpViewButton;

  @FXML
  private TextField usernameTextField;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

  @FXML
  public void login() {
    String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";

    connect = database.connectDB();

    try {
      if (usernameTextField.getText().isEmpty() || passwordPasswordField.getText().isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText("You need to enter a username and password");

        alert.showAndWait();
      } else {
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, usernameTextField.getText());
        prepare.setString(2, passwordPasswordField.getText());
        result = prepare.executeQuery();

        // kiem tra tk va mk
        if (result.next()) {
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Login Successful");
          alert.showAndWait();

          loginButton.getScene().getWindow().hide(); // tat scene login

          Parent root = FXMLLoader.load(getClass().getResource(
              "/org/example/btl/dashboardManager.fxml"));
          Stage stage = new Stage();
          Scene scene = new Scene(root);
          stage.setResizable(false); // tat nut maximine
          stage.setTitle("UET Library Management");
          stage.setScene(scene);
          stage.show();
        } else {
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Username or Password Incorrect");
          alert.showAndWait();
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* chi cho nhap username la so
  public void numbersOnly(KeyEvent event){
    if (event.getCharacter().matches("[^\\e\t\r\\d+$]")){
      event.consume();

      usernameTextField.setStyle("-fx-border-color:#e04040");
    }
    else {
      usernameTextField.setStyle("-fx-border-color:#fff");
    }
  }
  */
  public void loginView() {

  }

  public void signUpView() {
    try {
      //loginButton.getScene().getWindow().hide(); // tat scene login
      Stage stage = (Stage) signUpViewButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/signUp.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
