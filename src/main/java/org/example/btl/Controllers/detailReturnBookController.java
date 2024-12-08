package org.example.btl.Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.database;
import org.example.btl.informationUserName;
import org.example.btl.model.Book;
import org.example.btl.model.BorrowingRecord;

public class detailReturnBookController {
  @FXML
  private TextArea ISBNTextArea;

  @FXML
  private TextArea authorTextArea;

  @FXML
  private TextArea bookNameTextArea;

  @FXML
  private TextArea categoryTextArea;

  @FXML
  private HBox dashboardUserButton;

  @FXML
  private TextArea descriptionTextArea;

  @FXML
  private ImageView imageBookImageView;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox myBookButton;

  @FXML
  private TextArea publicationYearTextArea;

  @FXML
  private TextArea quantityTextArea;

  @FXML
  private Button returnBookButton;

  public void dashboardUserView(){
    try {
      Stage stage = (Stage) listBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/dashboardUser.fxml"));
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
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/listBookUser.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void myBookView(){
    try {
      Stage stage = (Stage) myBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/myBook.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setData(Book book, BorrowingRecord record){
    ISBNTextArea.setText(book.getISBN());
    ISBNTextArea.setEditable(false);

    bookNameTextArea.setText(book.getBookName());
    bookNameTextArea.setEditable(false);

    authorTextArea.setText(book.getAuthor());
    authorTextArea.setEditable(false);

    categoryTextArea.setText(book.getCategory());
    categoryTextArea.setEditable(false);

    publicationYearTextArea.setText(book.getPublicationYear());
    publicationYearTextArea.setEditable(false);

    quantityTextArea.setText(""+ book.getQuantity());
    quantityTextArea.setEditable(false);

    descriptionTextArea.setText(book.getDescription());
    descriptionTextArea.setEditable(false);

    Image image = new Image(book.getImageUrl());
    imageBookImageView.setImage(image);

    this.book = book;
    this.record = record;
  }

  Book book;
  BorrowingRecord record;

  private Connection connect;
  private PreparedStatement prepareCheck;
  private ResultSet resultCheck;

  private PreparedStatement prepareUpdate;
  private ResultSet resultUpdate;

  private PreparedStatement prepareChange;
  private ResultSet resultChange;

  private PreparedStatement prepareFix;
  private ResultSet resultFix;

  public void returnBook(){
    Alert alert;
    String sql = "SELECT * FROM borrowedhistory WHERE userName = ? AND ISBN = ?";
    connect = database.connectDB();
    try {
      prepareCheck = connect.prepareStatement(sql);
      prepareCheck.setString(1, informationUserName.userName);
      prepareCheck.setString(2, book.getISBN());
      resultCheck = prepareCheck.executeQuery();
      if (!resultCheck.next()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Sách chưa được mượn");
        alert.showAndWait();
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    sql = "SELECT * FROM borrowedhistory WHERE userName = ? AND ISBN = ? AND returnDate IS NOT null";
    try {
      prepareUpdate = connect.prepareStatement(sql);
      prepareUpdate.setString(1, informationUserName.userName);
      prepareUpdate.setString(2, book.getISBN());
      resultUpdate = prepareUpdate.executeQuery();
      if (resultUpdate.next()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Sách đã được trả rồi");
        alert.showAndWait();
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    int rowsAffected1 = 0;
    int rowsAffected2 = 0;

    sql = "UPDATE borrowedhistory SET returnDate = ? WHERE userName = ? AND ISBN = ?;";
    try {
      prepareChange = connect.prepareStatement(sql);
      prepareChange.setDate(1, Date.valueOf(LocalDate.now()));
      prepareChange.setString(2, informationUserName.userName);
      prepareChange.setString(3, book.getISBN());
      rowsAffected1 = prepareChange.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    sql = "UPDATE account SET numberBorrowedBooks = numberBorrowedBooks - 1 WHERE userName = ?";
    try {
      prepareFix = connect.prepareStatement(sql);
      prepareFix.setString(1, informationUserName.userName);
      rowsAffected2 = prepareFix.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (rowsAffected1 > 0 && rowsAffected2 > 0){
      informationUserName.numberBorrowedBooks--;
      alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Trả sách thành công");
      alert.showAndWait();
    } else {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Lỗi trả sách");
      alert.showAndWait();
    }
  }
}
