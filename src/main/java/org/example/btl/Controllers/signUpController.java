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
import org.example.btl.Database.database;

public class signUpController {

  @FXML
  private TextField confirmPasswordTextField;

  @FXML
  private TextField emailTextField;

  @FXML
  private Button loginViewButton;

  @FXML
  private TextField passwordTextField;

  @FXML
  private Button signUpButton;

  @FXML
  private Button signUpViewButton;

  @FXML
  private TextField usernameTextField;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

  public void signUp() {
    String sql = "INSERT INTO account (userName, password, email, numberBorrowedBooks, numberViolations) SELECT ?, ?, ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM account WHERE userName = ? OR email = ?);";

    connect = database.connectDB();
    try {
      prepare = connect.prepareStatement(sql);
      prepare.setString(1, usernameTextField.getText());
      prepare.setString(2, confirmPasswordTextField.getText());
      prepare.setString(3, emailTextField.getText());
      prepare.setInt(4, 0);
      prepare.setInt(5, 0);
      prepare.setString(6, usernameTextField.getText());
      prepare.setString(7, emailTextField.getText());
      int rowsAffected = prepare.executeUpdate();

      Alert alert;

      if (emailTextField.getText().isEmpty() || usernameTextField.getText().isEmpty()
          || passwordTextField.getText().isEmpty() || confirmPasswordTextField.getText().isEmpty()
          || emailTextField.getText().isEmpty()) {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy điền vào ô còn trống");
        alert.showAndWait();
      } else if (rowsAffected > 0) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Đăng ký thành công");
        alert.showAndWait();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/login.fxml"));
        Parent loginView = loader.load();
        Stage stage = (Stage) loginViewButton.getScene().getWindow();
        Scene scene = new Scene(loginView);
        stage.setScene(scene);
        stage.show();
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Email hoặc Username đã tồn tại");
        alert.showAndWait();
      }
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

  @FXML
  public void initialize() {

  }
}
