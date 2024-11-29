package org.example.btl.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

  @FXML
  public void initialize() {
    loginViewButton.setOnAction(event -> switchToLogin());
  }

  private void switchToLogin() {
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


}
