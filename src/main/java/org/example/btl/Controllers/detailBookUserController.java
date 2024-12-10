package org.example.btl.Controllers;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.database;
import org.example.btl.informationUserName;
import org.example.btl.model.Book;

public class detailBookUserController {
  @FXML
  private TextArea ISBNTextArea;

  @FXML
  private TextArea authorTextArea;

  @FXML
  private TextArea bookNameTextArea;

  @FXML
  private Button borrowBookButton;

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
  private TextField numberBorrowedBooksTextField;

  @FXML
  private TextField numberViolationsTextField;

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

  public void setBook(Book book){
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
  }

  public void showInformation(){
    numberBorrowedBooksTextField.setText("" + informationUserName.numberBorrowedBooks);
    numberBorrowedBooksTextField.setEditable(false);
    numberViolationsTextField.setText("" + informationUserName.numberViolations);
    numberViolationsTextField.setEditable(false);
  }

  Book book;

  private Connection connect;
  private PreparedStatement prepareUpdate;
  private ResultSet resultUpdate;

  private PreparedStatement prepareAdd;
  private ResultSet resultAdd;

  private PreparedStatement prepareFind;
  private ResultSet resultFind;

  private PreparedStatement prepareUpdate2;
  private ResultSet resultUpdate2;

  public void borrowBook(){
    Alert alert;
    if (informationUserName.numberViolations > 3 || informationUserName.numberBorrowedBooks > 30){
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Bạn không đủ điều kiện để mượn sách");
      alert.showAndWait();
      return;
    }

    String sql = "SELECT * FROM borrowedhistory WHERE ISBN = ? AND returnDate is null";
    connect = database.connectDB();
    try {
        prepareFind = connect.prepareStatement(sql);
        prepareFind.setString(1, book.getISBN());
        resultFind = prepareFind.executeQuery();
        if(resultFind.next()){
          alert = new Alert(AlertType.ERROR);
          alert.setTitle("Admin Message");
          alert.setHeaderText(null);
          alert.setContentText("Bạn chưa trả sách nên không thể mượn tiếp");
          alert.showAndWait();
          return;
        }
    } catch (Exception e) {
      e.printStackTrace();
    }

    int rowsAffected1 = 0;
    int rowsAffected2 = 0;

    sql = "UPDATE account SET numberBorrowedBooks = numberBorrowedBooks + 1 WHERE userName = ?";
    try {
      prepareUpdate = connect.prepareStatement(sql);
      prepareUpdate.setString(1, informationUserName.userName);
      rowsAffected1 = prepareUpdate.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    sql = "INSERT INTO borrowedhistory (ISBN, bookName, userName, borrowDate) VALUES (?, ?, ?, ?)";

    try {
      LocalDate localDate = LocalDate.now();
      prepareAdd = connect.prepareStatement(sql);
      prepareAdd.setString(1, book.getISBN());
      prepareAdd.setString(2, book.getBookName());
      prepareAdd.setString(3, informationUserName.userName);
      prepareAdd.setDate(4, Date.valueOf(localDate));
      rowsAffected2 = prepareAdd.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    sql = "UPDATE books SET quantity = quantity - 1 WHERE ISBN = ?";
    try {
      prepareUpdate2 = connect.prepareStatement(sql);
      prepareUpdate2.setString(1, book.getISBN());
      prepareUpdate2.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (rowsAffected1 > 0 && rowsAffected2 > 0){

      informationUserName.numberBorrowedBooks++;

      alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Mượn sách thành công");
      alert.showAndWait();
    } else {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Admin Message");
      alert.setHeaderText(null);
      alert.setContentText("Lỗi mượn sách");
      alert.showAndWait();
    }
  }

  int status = 0;

  private PreparedStatement prepareFind2;
  private ResultSet resultFind2;

  public void changeImage(){
    if (status == 0) status = 1;
    else status = 0;
    if (status == 1){
      String sql = "SELECT * FROM qr WHERE ISBN = ?";

      connect = database.connectDB();

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
    }
    else {
      Image image = new Image(book.getImageUrl());
      imageBookImageView.setImage(image);
    }
  }

  @FXML
  public void initialize(){
    showInformation();
  }
}
