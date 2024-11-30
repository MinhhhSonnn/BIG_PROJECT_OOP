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

  // tim admin
  private Connection connect;
  private PreparedStatement prepareAdmin;
  private ResultSet resultAdmin;
  private Statement statement;

  // tim user
  private PreparedStatement prepareUser;
  private ResultSet resultUser;

  public void login(){
    String sqlAdmin = "SELECT * FROM account WHERE userName = ? AND password = ? AND accountType = 'admin'";
    String sqlUser = "SELECT * FROM account WHERE userName = ? AND password = ? AND accountType = 'user'";

    connect = database.connectDB();
    try{
      prepareAdmin = connect.prepareStatement(sqlAdmin);
      prepareAdmin.setString(1, usernameTextField.getText());
      prepareAdmin.setString(2, passwordPasswordField.getText());
      resultAdmin = prepareAdmin.executeQuery();

      prepareUser = connect.prepareStatement(sqlUser);
      prepareUser.setString(1, usernameTextField.getText());
      prepareUser.setString(2, passwordPasswordField.getText());
      resultUser = prepareUser.executeQuery();

      Alert alert;

      if (usernameTextField.getText().isEmpty() || passwordPasswordField.getText().isEmpty()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy điền vào ô còn trống");
        alert.showAndWait();
      }
      else {
        // kiem tra tk va mk
        if (resultAdmin.next()){
          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Đăng nhập thành công");
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
        }
        else if(resultUser.next()){
          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Đăng nhập thành công");
          alert.showAndWait();

          loginButton.getScene().getWindow().hide(); // tat scene login

          Parent root = FXMLLoader.load(getClass().getResource(
              "/org/example/btl/dashboardUser.fxml"));
          Stage stage = new Stage();
          Scene scene = new Scene(root);
          stage.setResizable(false); // tat nut maximine
          stage.setTitle("UET Library Management");
          stage.setScene(scene);
          stage.show();
        }
        else {
          alert = new Alert(AlertType.ERROR);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Bạn nhập sai tài khoản hoặc mật khẩu");
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

  public void signUpView(){
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

  @FXML
  public void initialize(URL url, ResourceBundle rb){

  }

}