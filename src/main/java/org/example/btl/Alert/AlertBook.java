package org.example.btl.Alert;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class AlertBook {

  public static void showSuccess(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Thành công");
    alert.setHeaderText(null);
    alert.setContentText(message);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(
        AlertBook.class.getResource("/css/alert.css").toExternalForm()
    );
    dialogPane.getStyleClass().add("success-dialog");

    alert.showAndWait();
  }

  public static void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Lỗi");
    alert.setHeaderText(null);
    alert.setContentText(message);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(
        AlertBook.class.getResource("/css/alert.css").toExternalForm()
    );
    dialogPane.getStyleClass().add("error-dialog");

    alert.showAndWait();
  }

  public static void showWarning(String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Cảnh báo");
    alert.setHeaderText(null);
    alert.setContentText(message);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(
        AlertBook.class.getResource("/css/alert.css").toExternalForm()
    );
    dialogPane.getStyleClass().add("warning-dialog");

    alert.showAndWait();
  }

  public static boolean showConfirmation(String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Xác nhận");
    alert.setHeaderText(null);
    alert.setContentText(message);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(
        AlertBook.class.getResource("/css/alert.css").toExternalForm()
    );

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }

}
