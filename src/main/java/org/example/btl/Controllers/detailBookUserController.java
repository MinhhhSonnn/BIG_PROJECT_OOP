package org.example.btl.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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
  }

  public void borrowBook(){

  }
}
