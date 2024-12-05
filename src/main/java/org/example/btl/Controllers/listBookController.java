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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.database;
import org.example.btl.api.GoogleBooksAPI;
import org.example.btl.model.Book;

public class listBookController {
  @FXML
  private HBox listBookButton;

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

  @FXML
  private ImageView searchButton;

  private Connection connect;
  private PreparedStatement prepareDefault;
  private ResultSet resultDefault;
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

  private PreparedStatement prepareSearch2;
  private ResultSet resultSearch2;

  public ObservableList<Book> listBookSearch(){
    ObservableList<Book> returnBook = FXCollections.observableArrayList();

    String sql = "SELECT * "
        + "FROM books "
        + "WHERE bookName = ? "
        + "   OR author = ? "
        + "   OR category = ?;";

    connect = database.connectDB();

    try {
      Book book;
      prepareSearch2 = connect.prepareStatement(sql);
      prepareSearch2.setString(1, searchTextField.getText());
      prepareSearch2.setString(2, searchTextField.getText());
      prepareSearch2.setString(3, searchTextField.getText());
      resultSearch2 = prepareSearch2.executeQuery();
      while (resultSearch2.next()){
        book = new Book(resultSearch2.getString("bookName"), resultSearch2.getString("author"), resultSearch2.getString("ISBN"), resultSearch2.getInt("quantity"),
            resultSearch2.getString("description"), resultSearch2.getString("imageUrl"), resultSearch2.getString("publicationYear"), resultSearch2.getString("category"));

        returnBook.add(book);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnBook;
  }

  public ObservableList<Book> listBookDefault(){
    ObservableList<Book> returnBook = FXCollections.observableArrayList();

    String sql = "SELECT * FROM books";

    connect = database.connectDB();

    try{
      Book book;
      prepareDefault = connect.prepareStatement(sql);
      resultDefault = prepareDefault.executeQuery();
      while (resultDefault.next()){
        book = new Book(resultDefault.getString("bookName"), resultDefault.getString("author"), resultDefault.getString("ISBN"), resultDefault.getInt("quantity"),
            resultDefault.getString("description"), resultDefault.getString("imageUrl"), resultDefault.getString("publicationYear"), resultDefault.getString("category"));

        returnBook.add(book);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnBook;
  }

  boolean typeSearch = false;
  /*
  false la mac dinh
  true la hien thi theo search
  */
  ObservableList<Book> books;
  public void showBook(){
    if (typeSearch == false){
      books = listBookDefault();
    } else {
      books = listBookSearch();
    }

    bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    publicationYearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    tableView.setItems(books);
  }

  public void selectBook(){
    Book book = tableView.getSelectionModel().getSelectedItem();
    int index = tableView.getSelectionModel().getFocusedIndex();
    if (index < 0){
      return;
    }
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/detailBook.fxml"));
      Parent root = loader.load();

      detailBookController controller = loader.getController();

      controller.setBook(book);

      Stage stage = (Stage) dashboardManagerButton.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setResizable(false); // Tắt nút maximize
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private PreparedStatement prepareSearch1;
  private ResultSet resultSearch1;

  public void search(){
    String sql = "SELECT * "
        + "FROM books "
        + "WHERE bookName = ? "
        + "   OR author = ? "
        + "   OR category = ?;";

    connect = database.connectDB();
    try {
      Alert alert;
      prepareSearch1 = connect.prepareStatement(sql);
      prepareSearch1.setString(1, searchTextField.getText());
      prepareSearch1.setString(2, searchTextField.getText());
      prepareSearch1.setString(3, searchTextField.getText());
      resultSearch1 = prepareSearch1.executeQuery();

      if(searchTextField.getText().isEmpty()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy nhập thông tin sách vào ô tìm kiếm");
        alert.showAndWait();
      } else if (resultSearch1.next()) {
        typeSearch = true;
        showBook();
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Không tồn tại sách đang tìm");
        alert.showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize(){
    showBook();
    tableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 1) {
        selectBook();
      }
    });
  }
}
