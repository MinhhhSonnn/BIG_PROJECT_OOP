package org.example.btl.Controllers;

import java.io.File;
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
import org.example.btl.Database.Database;
import org.example.btl.InformationUserName;
import org.example.btl.model.Book;
import org.example.btl.model.BorrowingRecord;

public class DetailReturnBookController {

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

  public void dashboardUserView() {
    try {
      Stage stage = (Stage) listBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/DashboardUser.fxml"));
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
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/ListBookUser.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void myBookView() {
    try {
      Stage stage = (Stage) myBookButton.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/MyBook.fxml"));
      Scene scene = new Scene(root);
      stage.setResizable(false); // tat nut maximine
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setData(Book book, BorrowingRecord record) {
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

    quantityTextArea.setText("" + book.getQuantity());
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

  private PreparedStatement prepareUpdate2;
  private ResultSet resultUpdate2;

  public void returnBook() {
    Alert alert;
    String sql = "SELECT * FROM borrowedhistory WHERE userName = ? AND ISBN = ?";
    connect = Database.connectDB();
    try {
      prepareCheck = connect.prepareStatement(sql);
      prepareCheck.setString(1, InformationUserName.userName);
      prepareCheck.setString(2, book.getISBN());
      resultCheck = prepareCheck.executeQuery();
      if (!resultCheck.next()) {
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
      prepareUpdate.setString(1, InformationUserName.userName);
      prepareUpdate.setString(2, book.getISBN());
      resultUpdate = prepareUpdate.executeQuery();
      if (resultUpdate.next()) {
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
      prepareChange.setString(2, InformationUserName.userName);
      prepareChange.setString(3, book.getISBN());
      rowsAffected1 = prepareChange.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    sql = "UPDATE account SET numberBorrowedBooks = numberBorrowedBooks - 1 WHERE userName = ?";
    try {
      prepareFix = connect.prepareStatement(sql);
      prepareFix.setString(1, InformationUserName.userName);
      rowsAffected2 = prepareFix.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    sql = "UPDATE books SET quantity = quantity + 1 WHERE ISBN = ?";
    try {
      prepareUpdate2 = connect.prepareStatement(sql);
      prepareUpdate2.setString(1, book.getISBN());
      prepareUpdate2.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (rowsAffected1 > 0 && rowsAffected2 > 0) {
      InformationUserName.numberBorrowedBooks--;
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

  int status = 0;

  private PreparedStatement prepareFind2;
  private ResultSet resultFind2;

  public void changeImage() {
    if (status == 0) {
      status = 1;
    } else {
      status = 0;
    }
    if (status == 1) {
      String sql = "SELECT * FROM qr WHERE ISBN = ?";

      connect = Database.connectDB();

      try {
        prepareFind2 = connect.prepareStatement(sql);
        prepareFind2.setString(1, book.getISBN());
        resultFind2 = prepareFind2.executeQuery();
        resultFind2.next();
        String qrPath = resultFind2.getString("linkQR");
        // Chuyển đổi đường dẫn local thành URL
        File file = new File(qrPath);
        String imageUrl = file.toURI().toURL().toString();

        Image image = new Image(imageUrl);
        imageBookImageView.setImage(image);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      Image image = new Image(book.getImageUrl());
      imageBookImageView.setImage(image);
    }
  }
}
