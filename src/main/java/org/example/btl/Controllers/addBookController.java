package org.example.btl.Controllers;

import java.io.File;
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
  private HBox borrowHistoryButton;

  @FXML
  private TextField categoryNewTextField;

  @FXML
  private HBox changeManagerButton;

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

  public void changeManagerView(){
    try {
      Stage stage = (Stage) changeManagerButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/changeManager.fxml"));
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
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/borrowHistory.fxml"));
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

  private PreparedStatement prepareAddQR;
  private ResultSet resultAddQR;

  public void addBook(){
    Book book = GoogleBooksAPI.getBookInfo(ISBNTextField.getText());
    Alert alert;
    int quantity;

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
    else if (!isInteger(quantityTextField.getText()) || Integer.parseInt(quantityTextField.getText()) > 2147483640 || Integer.parseInt(quantityTextField.getText()) < 0){
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Số lượng không hợp lệ");
      alert.showAndWait();
    }
    else {
      quantity = Integer.parseInt(quantityTextField.getText());
      String sql = "SELECT * FROM books WHERE ISBN = ?";

      connect = database.connectDB();
      try {
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, ISBNTextField.getText());
        result = prepare.executeQuery();
        if (result.next()){
          alert = new Alert(AlertType.ERROR);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Sách đã tồn tại trong kho");
          alert.showAndWait();
        } else {
          sql = "INSERT INTO books (bookName, author, category, quantity, publicationYear, ISBN, imageUrl, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

          prepare = connect.prepareStatement(sql);

          prepare.setString(1, book.getBookName());
          prepare.setString(2, book.getAuthor());
          prepare.setString(3, book.getCategory());
          prepare.setInt(4, quantity);
          prepare.setString(5, book.getPublicationYear());
          prepare.setString(6, ISBNTextField.getText());
          prepare.setString(7, book.getImageUrl());
          prepare.setString(8, book.getDescription());

          int rowsAffected = prepare.executeUpdate();

          if (rowsAffected > 0) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("Thêm sách thành công");
            alert.showAndWait();
          }

          book.generateQRCode(ISBNTextField.getText() + ".png");

          sql = "INSERT INTO qr (ISBN, linkQR) VALUES (?, ?)";
          prepareAddQR = connect.prepareStatement(sql);
          prepareAddQR.setString(1, ISBNTextField.getText());
          prepareAddQR.setString(2, "D:\\subject\\oop\\BTL\\QrCodePNG\\" + ISBNTextField.getText() + ".png");
          prepareAddQR.executeUpdate();
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

  public void updateBook(){
    Alert alert;
    if (!isInteger(quantityNewTextField.getText()) || Integer.parseInt(quantityNewTextField.getText()) > 2147483640 ||  Integer.parseInt(quantityNewTextField.getText()) < 0){
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Số lượng không hợp lệ");
      alert.showAndWait();
      return;
    }
    String sql = "SELECT * FROM books WHERE ISBN = ?";

    connect = database.connectDB();
    try {
      prepareFind = connect.prepareStatement(sql);

      prepareFind.setString(1, ISBNNewTextField.getText());
      resultFind = prepareFind.executeQuery();
      if (ISBNNewTextField.getText().isEmpty()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy nhập ISBN để thay đổi thông tin sách");
        alert.showAndWait();
      } else if (resultFind.next()) {
        sql = "UPDATE books SET " +
            "bookName = CASE WHEN ? != '' THEN ? ELSE bookName END, " +
            "author = CASE WHEN ? != '' THEN ? ELSE author END, " +
            "category = CASE WHEN ? != '' THEN ? ELSE category END, " +
            "quantity = CASE WHEN ? != -1 THEN ? ELSE quantity END, " +
            "publicationYear = CASE WHEN ? != '' THEN ? ELSE publicationYear END, " +
            "description = CASE WHEN ? != '' THEN ? ELSE description END " +
            "WHERE ISBN = ?";

        try {
          prepareUpdate = connect.prepareStatement(sql);

          prepareUpdate.setString(1, bookNameNewTextField.getText());
          prepareUpdate.setString(2, bookNameNewTextField.getText());
          prepareUpdate.setString(3, authorNewTextField.getText());
          prepareUpdate.setString(4, authorNewTextField.getText());
          prepareUpdate.setString(5, categoryNewTextField.getText());
          prepareUpdate.setString(6, categoryNewTextField.getText());
          if (quantityNewTextField.getText().isEmpty()){
            prepareUpdate.setInt(7, -1);
            prepareUpdate.setInt(8, -1);
          } else {
            prepareUpdate.setInt(7, Integer.parseInt(quantityNewTextField.getText()));
            prepareUpdate.setInt(8, Integer.parseInt(quantityNewTextField.getText()));
          }
          prepareUpdate.setString(9, publicationYearNewTextField.getText());
          prepareUpdate.setString(10, publicationYearNewTextField.getText());
          prepareUpdate.setString(11, descriptionNewTextField.getText());
          prepareUpdate.setString(12, descriptionNewTextField.getText());
          prepareUpdate.setString(13, ISBNNewTextField.getText());

          prepareUpdate.executeUpdate();

          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Cập nhật sách thành công");
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

  private PreparedStatement prepareDelete1;
  private ResultSet resultUDelete1;

  private PreparedStatement prepareDelete2;
  private ResultSet resultUDelete2;

  private PreparedStatement prepareDelete3;
  private ResultSet resultUDelete3;

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
          prepareDelete1 = connect.prepareStatement(sql);
          prepareDelete1.setString(1, ISBNOldTextField.getText());
          prepareDelete1.executeUpdate();

          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Xoá sách thành công");
          alert.showAndWait();
        } catch (Exception e) {
          e.printStackTrace();
        }

        sql = "DELETE FROM borrowedhistory WHERE ISBN = ?";
        try {
          prepareDelete2 = connect.prepareStatement(sql);
          prepareDelete2.setString(1, ISBNOldTextField.getText());
          prepareDelete2.executeUpdate();
        } catch (Exception e) {
          e.printStackTrace();
        }

        sql = "DELETE FROM qr WHERE ISBN = ?";
        try {
          prepareDelete3 = connect.prepareStatement(sql);
          prepareDelete3.setString(1, ISBNOldTextField.getText());
          prepareDelete3.executeUpdate();
        } catch (Exception e) {
          e.printStackTrace();
        }

        File file = new File("D:\\subject\\oop\\BTL\\QrCodePNG\\" + ISBNOldTextField.getText() + ".png");
        file.delete();

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


