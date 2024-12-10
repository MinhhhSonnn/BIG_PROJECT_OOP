package org.example.btl.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.btl.Database.Database;

public class PasswordRecoveryStep3Controller {

  @FXML
  private TextField acceptPassword;

  @FXML
  private Button changePasswordButton;

  @FXML
  private Button loginViewButton;

  @FXML
  private TextField passwordNewTextField;

  @FXML
  private Button signUpViewButton;

  public void signUpView() {
    try {
      Stage stage = (Stage) signUpViewButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/SignUp.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loginView() {
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

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

  private String email;

  public void setEmail(String email) {
    this.email = email;
  }

  public void changePassword() {
    Alert alert;
    if (passwordNewTextField.getText().isEmpty() || acceptPassword.getText().isEmpty()) {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Hãy điền vào ô còn trống");
      alert.showAndWait();
    } else if (!passwordNewTextField.getText().equals(acceptPassword.getText())) {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Mật khẩu không giống nhau");
      alert.showAndWait();
    } else {
      String sql = "UPDATE account "
          + "SET password = ? "
          + "WHERE email = ?;";

      connect = Database.connectDB();
      try {
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, acceptPassword.getText());
        prepare.setString(2, email);
        prepare.executeUpdate();

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Thay đổi mật khẩu thành công");
        alert.showAndWait();

        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/login.fxml"));
          Parent loginView = loader.load();
          Stage stage = (Stage) loginViewButton.getScene().getWindow();
          Scene scene = new Scene(loginView);
          stage.setScene(scene);
          stage.show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
