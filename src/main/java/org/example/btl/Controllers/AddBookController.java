package org.example.btl.Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
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

public class AddBookController {

  @FXML
  private TextField ISBNNewTextField;

  @FXML
  private TextField ISBNOldTextField;

  @FXML
  private TextField ISBNTextField;

  @FXML
  private Button addButton;

  @FXML
  private TextField authorNewTextField;

  @FXML
  private TextField bookNameNewTextField;

  @FXML
  private TextField categoryNewTextField;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private Button deleteButton;

  @FXML
  private TextField descriptionNewTextField;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TextField publicationYearNewTextField;

  @FXML
  private TextField quantityNewTextField;

  @FXML
  private TextField quantityTextField;

  @FXML
  private Button updateButton;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

  @FXML
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

  private boolean insertBookToDatabase(Book book, int quantity) {
    String sql =
        "INSERT INTO books (bookName, author, category, quantity, publicationYear, ISBN, imageUrl, description) "
            +
            "SELECT ?, ?, ?, ?, ?, ?, ?, ? " +
            "WHERE NOT EXISTS (SELECT 1 FROM books WHERE ISBN = ?);";

    try (Connection connect = database.connectDB();
        PreparedStatement prepare = connect.prepareStatement(sql)) {

      prepare.setString(1, book.getBookName());
      prepare.setString(2, book.getAuthor());
      prepare.setString(3, book.getCategory());
      prepare.setInt(4, quantity);
      prepare.setString(5, book.getPublicationYear());
      prepare.setString(6, ISBNTextField.getText());
      prepare.setString(7, book.getImageUrl());
      prepare.setString(8, book.getDescription());
      prepare.setString(9, book.getISBN());

      int rowsAffected = prepare.executeUpdate();

      return rowsAffected > 0;
    } catch (Exception e) {
      throw new RuntimeException("Lỗi khi thêm sách vào database", e);
    }
  }

  private void showAlert(AlertType type, String content) {
    Alert alert = new Alert(type);
    alert.setTitle("Admin Message");
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }


  private PreparedStatement prepareFind;
  private ResultSet resultFind;

  private PreparedStatement prepareUpdate;
  private ResultSet resultUpdate;

//  public void updateBook(){
//    Alert alert;
//    String sql = "SELECT * FROM books WHERE ISBN = ?";
//
//    connect = database.connectDB();
//    try {
//      prepareFind = connect.prepareStatement(sql);
//
//      prepareFind.setString(1, ISBNNewTextField.getText());
//      resultFind = prepareFind.executeQuery();
//      if (ISBNNewTextField.getText().isEmpty()){
//        alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Admin Message");
//        alert.setHeaderText(null);
//        alert.setContentText("Hãy nhập ISBN để thay đổi thông tin sách");
//        alert.showAndWait();
//      } else if (resultFind.next()) {
//        sql = "UPDATE books SET " +
//            "bookName = CASE WHEN ? != '' THEN ? ELSE bookName END, " +
//            "author = CASE WHEN ? != '' THEN ? ELSE author END, " +
//            "category = CASE WHEN ? != '' THEN ? ELSE category END, " +
//            "quantity = CASE WHEN ? != -1 THEN ? ELSE quantity END, " +
//            "publicationYear = CASE WHEN ? != '' THEN ? ELSE publicationYear END, " +
//            "description = CASE WHEN ? != '' THEN ? ELSE description END " +
//            "WHERE ISBN = ?";
//
//        try {
//          prepareUpdate = connect.prepareStatement(sql);
//
//          prepareUpdate.setString(1, bookNameNewTextField.getText());
//          prepareUpdate.setString(2, bookNameNewTextField.getText());
//          prepareUpdate.setString(3, authorNewTextField.getText());
//          prepareUpdate.setString(4, authorNewTextField.getText());
//          prepareUpdate.setString(5, categoryNewTextField.getText());
//          prepareUpdate.setString(6, categoryNewTextField.getText());
//          if (quantityNewTextField.getText().isEmpty()){
//            prepareUpdate.setInt(7, -1);
//            prepareUpdate.setInt(8, -1);
//          } else {
//            prepareUpdate.setInt(7, Integer.parseInt(quantityNewTextField.getText()));
//            prepareUpdate.setInt(8, Integer.parseInt(quantityNewTextField.getText()));
//          }
//          prepareUpdate.setString(9, publicationYearNewTextField.getText());
//          prepareUpdate.setString(10, publicationYearNewTextField.getText());
//          prepareUpdate.setString(11, descriptionNewTextField.getText());
//          prepareUpdate.setString(12, descriptionNewTextField.getText());
//          prepareUpdate.setString(13, ISBNNewTextField.getText());
//
//          prepareUpdate.executeUpdate();
//
//          alert = new Alert(AlertType.INFORMATION);
//          alert.setTitle("Admin Message");
//          alert.setHeaderText(null);
//          alert.setContentText("Cập nhật sách thành công");
//          alert.showAndWait();
//
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//      } else {
//        alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Admin Message");
//        alert.setHeaderText(null);
//        alert.setContentText("ISBN không có trong thư viện");
//        alert.showAndWait();
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  private PreparedStatement prepareDelete;
  private ResultSet resultUDelete;

  public void deleteBook(){
    Alert alert;
    String sql = "SELECT * FROM books WHERE ISBN = ?";

    connect = database.connectDB();
    try {
      prepareFind = connect.prepareStatement(sql);
      prepareFind.setString(1, ISBNOldTextField.getText());
      resultFind = prepareFind.executeQuery();

      if (ISBNOldTextField.getText().isEmpty()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy nhập ISBN để xoá thông tin sách");
        alert.showAndWait();
      } else if (resultFind.next()) {
        sql = "DELETE FROM books WHERE ISBN = ?";
        try {
          prepareDelete = connect.prepareStatement(sql);
          prepareDelete.setString(1, ISBNOldTextField.getText());
          prepareDelete.executeUpdate();

          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Xoá sách thành công");
          alert.showAndWait();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("ISBN không có trong thư viện");
        alert.showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


