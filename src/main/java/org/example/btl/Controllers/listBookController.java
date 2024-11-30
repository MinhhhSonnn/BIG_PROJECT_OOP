package org.example.btl.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class listBookController {
  @FXML
  private HBox addBookButton;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TextField searchTextField;

  public void dashboardManagerView(){
    try {
      Stage stage = (Stage) dashboardManagerButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/dashboardManager.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void listManagerView(){
    try {
      Stage stage = (Stage) listManagerButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/listManager.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void listUserView(){
    try {
      Stage stage = (Stage) listUserButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/listUser.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addBookView(){
    try {
      Stage stage = (Stage) addBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/addBook.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
