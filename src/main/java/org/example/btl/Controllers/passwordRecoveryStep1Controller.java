package org.example.btl.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.btl.Database.database;

public class passwordRecoveryStep1Controller {

  @FXML
  private Button acceptButton;

  @FXML
  private TextField emailTextField;

  @FXML
  private Button loginViewButton;

  @FXML
  private Button signUpViewButton;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

  public void signUpView() {
    try {
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

  public void loginView(){
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/login.fxml"));
      Parent loginView = loader.load();
      Stage stage = (Stage) loginViewButton.getScene().getWindow();
      Scene scene = new Scene(loginView);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void accept() {
    String sql = "Select * FROM account WHERE email = ?";

    connect = database.connectDB();
    try {
      prepare = connect.prepareStatement(sql);
      prepare.setString(1, emailTextField.getText());
      result = prepare.executeQuery();

      Alert alert;

      if (emailTextField.getText().isEmpty()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy điền vào ô còn trống");
        alert.showAndWait();
      } else if (result.next()){
        try {

          FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/passwordRecoveryStep2.fxml"));
          Parent root = loader.load();

          passwordRecoveryStep2Controller controller = loader.getController();
          controller.setEmail(emailTextField.getText());

          Stage stage = (Stage) loginViewButton.getScene().getWindow();
          Scene scene = new Scene(root);
          stage.setResizable(false); // tat nut maximine
          stage.setTitle("UET Library Management");
          stage.setScene(scene);
          stage.show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Email không tồn tại");
        alert.showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
