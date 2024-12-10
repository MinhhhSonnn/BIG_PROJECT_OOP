package org.example.btl.Controllers;

import static org.example.btl.Alert.AlertUtil.showAlert;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.btl.service.PasswordService;

public class ForgotPasswordController {

  @FXML
  private TextField emailField;

  private PasswordService passwordService;

  public ForgotPasswordController() {
    this.passwordService = new PasswordService();
  }

  @FXML
  private void handleSendButtonAction() {
    String email = emailField.getText().trim();

    if (email.isEmpty()) {
      showAlert(AlertType.ERROR, "Email không được để trống.");
      return;
    }

    if (!isValidEmail(email)) {
      showAlert(AlertType.ERROR, "Định dạng email không hợp lệ.");
      return;
    }

    try {
      passwordService.sendExistingPassword(email);
      showAlert(AlertType.INFORMATION, "Email khôi phục mật khẩu đã được gửi.");

    } catch (RuntimeException e) {
      showAlert(AlertType.ERROR, e.getMessage());
    }
  }

  //kiem tra dinh dang email
  private boolean isValidEmail(String email) {
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    return email.matches(emailRegex);
  }

}
