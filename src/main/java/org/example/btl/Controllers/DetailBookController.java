package org.example.btl.Controllers;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import org.example.btl.Database.Database;
import org.example.btl.model.Book;

public class DetailBookController {

  @FXML
  private HBox listBookButton;

  @FXML
  private TextArea ISBNTextArea;

  @FXML
  private HBox addBookButton;

  @FXML
  private TextArea authorTextArea;

  @FXML
  private TextArea bookNameTextArea;

  @FXML
  private TextArea categoryTextArea;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private TextArea descriptionTextArea;

  @FXML
  private ImageView imageBookImageView;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TextArea publicationYearTextArea;

  @FXML
  private TextArea quantityTextArea;

  @FXML
  private HBox borrowHistoryButton;

  @FXML
  private HBox changeManagerButton;

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

  public void setBook(Book book) {
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
  }

  int status = 0;
  Book book = null;

  private Connection connect;
  private PreparedStatement prepareFind;
  private ResultSet resultFind;

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
        prepareFind = connect.prepareStatement(sql);
        prepareFind.setString(1, book.getISBN());
        resultFind = prepareFind.executeQuery();
        resultFind.next();
        String qrPath = resultFind.getString("linkQR");
        File file = new File(qrPath);
        String imageUrl = file.toURI().toString();
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
