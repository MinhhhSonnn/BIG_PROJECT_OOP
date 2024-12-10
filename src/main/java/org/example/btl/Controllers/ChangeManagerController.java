package org.example.btl.Controllers;

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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.Database;

public class ChangeManagerController {

  @FXML
  private TextField IDNewTextField;

  @FXML
  private TextField IDDeleteTextField;

  @FXML
  private HBox addBookButton;

  @FXML
  private Button addButton;

  @FXML
  private HBox borrowHistoryButton;

  @FXML
  private HBox changeManagerButton;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private Button deleteButton;

  @FXML
  private TextField emailAddTextField;

  @FXML
  private TextField emailNewTextField;

  @FXML
  private TextField employeeNameAddTextField;

  @FXML
  private TextField employeeNameNewTextField;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TextField phoneAddTextField;

  @FXML
  private TextField phoneNewTextField;

  @FXML
  private TextField positionAddTextField;

  @FXML
  private TextField positionNewTextField;

  @FXML
  private TextField sexAddTextField;

  @FXML
  private TextField sexNewTextField;

  @FXML
  private Button updateButton;

  public void dashboardManagerView() {
    try {
      Stage stage = (Stage) dashboardManagerButton.getScene().getWindow();
      Parent root = FXMLLoader.load(
          getClass().getResource("/org/example/btl/DashboardManager.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void listManagerView() {
    try {
      Stage stage = (Stage) listManagerButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/ListManager.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void listUserView() {
    try {
      Stage stage = (Stage) listUserButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/ListUser.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void listBookView() {
    try {
      Stage stage = (Stage) listBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/ListBook.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addBookView() {
    try {
      Stage stage = (Stage) addBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/EditBook.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void changeManagerView() {
    try {
      Stage stage = (Stage) changeManagerButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/ChangeManager.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void borrowHistoryView() {
    try {
      Stage stage = (Stage) borrowHistoryButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/BorrowHistory.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Connection connect;
  private PreparedStatement prepareAdd;
  private ResultSet resultAdd;
  private Statement statement;

  public void addEmployee() {
    Alert alert;
    if (employeeNameAddTextField.getText().isEmpty() || sexAddTextField.getText().isEmpty()
        || positionAddTextField.getText().isEmpty() || phoneAddTextField.getText().isEmpty()
        || emailAddTextField.getText().isEmpty()) {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Hãy điền vào ô còn trống để thêm nhân viên");
      alert.showAndWait();
    } else {
      String sql = "SELECT * FROM employees WHERE phone = ? OR email = ?";

      connect = Database.connectDB();
      try {
        prepareAdd = connect.prepareStatement(sql);
        prepareAdd.setString(1, phoneAddTextField.getText());
        prepareAdd.setString(2, emailAddTextField.getText());
        resultAdd = prepareAdd.executeQuery();
        if (resultAdd.next()) {
          alert = new Alert(AlertType.ERROR);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Nhân viên đã có trong danh sách");
          alert.showAndWait();
        } else {
          sql = "INSERT INTO employees (employeeName, sex, position, phone, email) VALUES (?, ?, ?, ?, ?)";

          prepareAdd = connect.prepareStatement(sql);
          prepareAdd.setString(1, employeeNameAddTextField.getText());
          prepareAdd.setString(2, sexAddTextField.getText());
          prepareAdd.setString(3, positionAddTextField.getText());
          prepareAdd.setString(4, phoneAddTextField.getText());
          prepareAdd.setString(5, emailAddTextField.getText());

          int rowsAffected = prepareAdd.executeUpdate();

          if (rowsAffected > 0) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("Thêm nhân viên thành công");
            alert.showAndWait();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private PreparedStatement prepareFind;
  private ResultSet resultFind;

  private PreparedStatement prepareUpdate;
  private ResultSet resultUpdate;

  public void updateEmployee() {
    Alert alert;
    String sql = "SELECT * FROM employees WHERE ID = ?";

    connect = Database.connectDB();
    try {
      prepareFind = connect.prepareStatement(sql);
      prepareFind.setString(1, IDNewTextField.getText());
      resultFind = prepareFind.executeQuery();

      if (IDNewTextField.getText().isEmpty()) {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy nhập ID để cập nhật thông tin nhân viên");
        alert.showAndWait();
      } else if (resultFind.next()) {
        sql = "UPDATE employees " +
            "SET " +
            "employeeName = CASE WHEN ? != '' THEN ? ELSE employeeName END, " +
            "sex = CASE WHEN ? != '' THEN ? ELSE sex END, " +
            "position = CASE WHEN ? != '' THEN ? ELSE position END, " +
            "phone = CASE WHEN ? != '' THEN ? ELSE phone END, " +
            "email = CASE WHEN ? != '' THEN ? ELSE email END " +
            "WHERE ID = ?";

        try {
          prepareUpdate = connect.prepareStatement(sql);

          prepareUpdate.setString(1, employeeNameNewTextField.getText());
          prepareUpdate.setString(2, employeeNameNewTextField.getText());
          prepareUpdate.setString(3, sexNewTextField.getText());
          prepareUpdate.setString(4, sexNewTextField.getText());
          prepareUpdate.setString(5, positionNewTextField.getText());
          prepareUpdate.setString(6, positionNewTextField.getText());
          prepareUpdate.setString(7, phoneNewTextField.getText());
          prepareUpdate.setString(8, phoneNewTextField.getText());
          prepareUpdate.setString(9, emailNewTextField.getText());
          prepareUpdate.setString(10, emailNewTextField.getText());
          prepareUpdate.setInt(11, Integer.parseInt(IDNewTextField.getText()));

          prepareUpdate.executeUpdate();

          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Cập nhật nhân viên thành công");
          alert.showAndWait();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("ID không có trong danh sách nhân viên");
        alert.showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private PreparedStatement prepareDelete;
  private ResultSet resultDelete;

  public void deleteEmployee() {
    Alert alert;
    String sql = "SELECT * FROM employees WHERE ID = ?";

    connect = Database.connectDB();
    try {
      prepareFind = connect.prepareStatement(sql);
      prepareFind.setString(1, IDDeleteTextField.getText());
      resultFind = prepareFind.executeQuery();

      if (IDDeleteTextField.getText().isEmpty()) {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy nhập ID để xoá thông tin nhân viên");
        alert.showAndWait();
      } else if (resultFind.next()) {
        sql = "DELETE FROM employees WHERE ID = ?";
        try {
          prepareDelete = connect.prepareStatement(sql);
          prepareDelete.setString(1, IDDeleteTextField.getText());
          prepareDelete.executeUpdate();

          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Xoá nhân viên thành công");
          alert.showAndWait();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("ID không có trong danh sách nhân viên");
        alert.showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
