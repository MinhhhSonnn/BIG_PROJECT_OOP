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
import org.example.btl.Database.database;
import org.example.btl.api.GoogleBooksAPI;
import org.example.btl.model.Book;

public class addBookController {
  @FXML
  private TextField ISBNTextField;

  @FXML
  private Button addButton;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TextField quantityTextField;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

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

  public void listBookView(){
    try {
      Stage stage = (Stage) listBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/listBook.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // kiem tra quantity co phai int hay k
  public static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public void addBook(){
    Book book = GoogleBooksAPI.getBookInfo(ISBNTextField.getText());
    Alert alert;

    if (ISBNTextField.getText().isEmpty() || quantityTextField.getText().isEmpty()){
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Hãy điền vào ô còn trống");
      alert.showAndWait();
    }
    else if (book == null){
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("ISBN không hợp lệ");
      alert.showAndWait();
    }
    else if (!isInteger(quantityTextField.getText()) || Integer.parseInt(quantityTextField.getText()) > 254 || Integer.parseInt(quantityTextField.getText()) < 0){
      System.out.println(Integer.parseInt(quantityTextField.getText()));
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Số lượng không hợp lệ");
      alert.showAndWait();
    }
    else {
      String sql = "INSERT INTO books (bookName, author, category, quantity, publicationYear, ISBN, imageUrl, description) " +
          "SELECT ?, ?, ?, ?, ?, ?, ?, ? " +
          "WHERE NOT EXISTS (SELECT 1 FROM books WHERE ISBN = ?);";

      connect = database.connectDB();
      try {
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, book.getBookName());
        prepare.setString(2, book.getAuthor());
        prepare.setString(3, book.getCategory());
        prepare.setInt(4, Integer.parseInt(quantityTextField.getText()));
        prepare.setString(5, book.getPublicationYear());
        prepare.setString(6, book.getISBN());
        prepare.setString(7, book.getImageUrl());
        prepare.setString(8, book.getDescription());
        prepare.setInt(9, Integer.parseInt(quantityTextField.getText()));

        int rowsAffected = prepare.executeUpdate();

        if (rowsAffected > 0){
          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Thêm sách thành công");
          alert.showAndWait();
        }
        else {
          alert = new Alert(AlertType.ERROR);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Sách đã tồn tại trong kho");
          alert.showAndWait();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
