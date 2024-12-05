package org.example.btl.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class passwordRecoveryStep2Controller {
  @FXML
  private Button acceptButton;

  @FXML
  private TextField codeTextField;

  @FXML
  private Button loginViewButton;

  @FXML
  private Button signUpViewButton;

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

  private String email;

  public void setEmail(String email){
    this.email = email;
  }

  private String code;

  public void setCode(String code){
    this.code = code;
  }

  public void accept(){
    Alert alert;
    if (codeTextField.getText().isEmpty()){
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Hãy điền vào ô còn trống");
      alert.showAndWait();
    } else if (codeTextField.getText().equals(code)){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/passwordRecoveryStep3.fxml"));
        Parent root = loader.load();

        passwordRecoveryStep3Controller controller = loader.getController();
        controller.setEmail(email);

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
      alert.setContentText("Bạn đã nhập sai code");
      alert.showAndWait();
    }
  }
}
