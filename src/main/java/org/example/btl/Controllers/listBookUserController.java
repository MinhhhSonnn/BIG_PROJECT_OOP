package org.example.btl.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.database;
import org.example.btl.model.Book;

public class listBookUserController {

  @FXML
  private TableColumn<Book, String> ISBNColumn;

  @FXML
  private TableColumn<Book, String> authorColumn;

  @FXML
  private TableColumn<Book, String> bookNameColumn;

  @FXML
  private TableColumn<Book, String> categoryColumn;

  @FXML
  private HBox dashboardUserButton;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox myBookButton;

  @FXML
  private TableColumn<Book, String> publicationYearColumn;

  @FXML
  private TableColumn<Book, Integer> quantityColumn;

  @FXML
  private ImageView searchButton;

  @FXML
  private TextField searchTextField;

  @FXML
  private TableView<Book> tableView;

  @FXML
  private ImageView top1BookImageView;

  @FXML
  private ImageView top2BookImageView;

  @FXML
  private ImageView top3BookImageView;

  private Connection connect;
  private PreparedStatement prepareDefault;
  private ResultSet resultDefault;
  private Statement statement;

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
      if (book == null) return;
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/detailBookUser.fxml"));
      Parent root = loader.load();

      detailBookUserController controller = loader.getController();

      controller.setBook(book);

      Stage stage = (Stage) listBookButton.getScene().getWindow();
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

  private PreparedStatement prepareTopBook;
  private ResultSet resultTopBook;

  private PreparedStatement prepareFindTopBook;
  private ResultSet resultFindTopBook;

  public void showTopBook(){
    String sql = "SELECT ISBN, COUNT(*) AS borrowCount " +
        "FROM borrowedhistory " +
        "GROUP BY ISBN " +
        "ORDER BY borrowCount DESC " +
        "LIMIT 3";

    connect = database.connectDB();
    try {
      prepareTopBook = connect.prepareStatement(sql);
      resultTopBook = prepareTopBook.executeQuery();

      int count = 1;

      while(resultTopBook.next()){
        sql = "SELECT * FROM books WHERE ISBN = ?";
        prepareFindTopBook = connect.prepareStatement(sql);
        prepareFindTopBook.setString(1, resultTopBook.getString("ISBN"));
        resultFindTopBook = prepareFindTopBook.executeQuery();
        resultFindTopBook.next();
        Book book = new Book(resultFindTopBook.getString("bookName"), resultFindTopBook.getString("author"), resultFindTopBook.getString("ISBN"), resultFindTopBook.getInt("quantity"),
            resultFindTopBook.getString("description"), resultFindTopBook.getString("imageUrl"), resultFindTopBook.getString("publicationYear"), resultFindTopBook.getString("category"));
        Image image = new Image(book.getImageUrl());
        if (count == 1){
          top1Book = book;
          top1BookImageView.setImage(image);
          count++;
        } else if (count == 2) {
          top2Book = book;
          top2BookImageView.setImage(image);
          count++;
        } else if (count == 3){
          top3Book = book;
          top3BookImageView.setImage(image);
          count++;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  Book top1Book;
  Book top2Book;
  Book top3Book;

  public void top1Book(){
    try{
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/detailBookUser.fxml"));
      Parent root = loader.load();

      detailBookUserController controller = loader.getController();

      controller.setBook(top1Book);

      Stage stage = (Stage) listBookButton.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setResizable(false); // Tắt nút maximize
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void top2Book(){
    try{
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/detailBookUser.fxml"));
      Parent root = loader.load();

      detailBookUserController controller = loader.getController();

      controller.setBook(top2Book);

      Stage stage = (Stage) listBookButton.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setResizable(false); // Tắt nút maximize
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void top3Book(){
    try{
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/detailBookUser.fxml"));
      Parent root = loader.load();

      detailBookUserController controller = loader.getController();

      controller.setBook(top3Book);

      Stage stage = (Stage) listBookButton.getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setResizable(false); // Tắt nút maximize
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize(){
    showTopBook();
    showBook();
    tableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 1) {
        selectBook();
      }
    });
  }
}
