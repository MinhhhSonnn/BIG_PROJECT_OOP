package org.example.btl.Alert;
import javafx.scene.control.Alert;
public class AlertUtil {
  public static void showAlert(Alert.AlertType type, String content) {
    Alert alert = new Alert(type);
    alert.setTitle("Admin Message");
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
