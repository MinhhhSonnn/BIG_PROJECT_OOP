package org.example.btl.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.database;
import org.example.btl.api.GoogleBooksAPI;
import org.example.btl.model.Book;

public class listBookController {
  @FXML
  private TableView<Book> tableView;

  @FXML
  private TableColumn<Book, String> ISBNColumn;

  @FXML
  private HBox addBookButton;

  @FXML
  private TableColumn<Book, String> authorColumn;

  @FXML
  private TableColumn<Book, String> bookNameColumn;

  @FXML
  private TableColumn<Book, String> categoryColumn;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TableColumn<Book, String> publicationYearColumn;

  @FXML
  private TableColumn<Book, Integer> quantityColumn;

  @FXML
  private TextField searchTextField;

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

  public ObservableList<Book> listBook(){
    ObservableList<Book> returnBook = FXCollections.observableArrayList();

    String sql = "SELECT * FROM books";

    connect = database.connectDB();

    try{
      Book book;
      prepare = connect.prepareStatement(sql);
      result = prepare.executeQuery();
      while (result.next()){
        book = new Book(result.getString("bookName"), result.getString("author"), result.getString("ISBN"), result.getInt("quantity"),
            result.getString("description"), result.getString("imageUrl"), result.getString("publicationYear"), result.getString("category"));

        returnBook.add(book);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnBook;
  }

  ObservableList<Book> books;
  public void showBook(){
    books = listBook();

    bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    publicationYearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    tableView.setItems(books);
  }

  @FXML
  public void initialize(){
    showBook();
  }
}
