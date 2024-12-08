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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.database;
import org.example.btl.model.Book;
import org.example.btl.model.Reader;

public class listUserController {
  @FXML
  private HBox borrowHistoryButton;

  @FXML
  private HBox changeManagerButton;

  @FXML
  private HBox addBookButton;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private TableColumn<Reader, String> emailColumn;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TableColumn<Reader, Integer> numberBorrowedBooksColumn;

  @FXML
  private TableColumn<Reader, Integer> numberViolations;

  @FXML
  private TableColumn<Reader, String> passwordColumn;

  @FXML
  private ImageView searchButton;

  @FXML
  private TextField searchTextField;

  @FXML
  private TableView<Reader> tableView;

  @FXML
  private TableColumn<Reader, String> userNameColumn;

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

  private PreparedStatement prepareSearch2;
  private ResultSet resultSearch2;

  public ObservableList<Reader> listReaderSearch(){
    ObservableList<Reader> returnReader = FXCollections.observableArrayList();

    String sql = "SELECT * "
        + "FROM account "
        + "WHERE userName = ? "
        + "   OR password = ? "
        + "   OR email = ?;";

    connect = database.connectDB();

    try {
      Reader reader;
      prepareSearch2 = connect.prepareStatement(sql);
      prepareSearch2.setString(1, searchTextField.getText());
      prepareSearch2.setString(2, searchTextField.getText());
      prepareSearch2.setString(3, searchTextField.getText());
      resultSearch2 = prepareSearch2.executeQuery();
      while(resultSearch2.next()){
        reader = new Reader(resultSearch2.getString("userName"), resultSearch2.getString("password"), resultSearch2.getString("email"), resultSearch2.getInt("numberBorrowedBooks"), resultSearch2.getInt("numberViolations"));

        returnReader.add(reader);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnReader;
  }

  public ObservableList<Reader> listUserDefault(){
    ObservableList<Reader> returnReader = FXCollections.observableArrayList();

    String sql = "SELECT * FROM account WHERE accountType = 'user'";

    connect = database.connectDB();

    try{
      Reader reader;
      prepareDefault = connect.prepareStatement(sql);
      resultDefault = prepareDefault.executeQuery();
      while(resultDefault.next()){
        reader = new Reader(resultDefault.getString("userName"), resultDefault.getString("password"), resultDefault.getString("email"), resultDefault.getInt("numberBorrowedBooks"), resultDefault.getInt("numberViolations"));

        returnReader.add(reader);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnReader;
  }

  boolean typeSearch = false;
  /*
  false la mac dinh
  true la hien thi theo search
  */
  ObservableList<Reader> readers;
  public void showReader(){
    if (typeSearch == false){
      readers = listUserDefault();
    } else {
      readers = listReaderSearch();
    }

    userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    numberBorrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfBooksBorrow"));
    numberViolations.setCellValueFactory(new PropertyValueFactory<>("numberOfViolations"));

    tableView.setItems(readers);
  }

  public void selectReader(){
    Reader reader = tableView.getSelectionModel().getSelectedItem();
    int index = tableView.getSelectionModel().getFocusedIndex();
    if (index < 0){
      return;
    }
    try {
      if (reader == null) return;
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/detailUser.fxml"));
      Parent root = loader.load();

      detailUserController controller = loader.getController();
      controller.setRecord(reader);

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
        + "FROM account "
        + "WHERE userName = ? "
        + "   OR password = ? "
        + "   OR email = ? "
        + "   OR accountType = ?;";

    connect = database.connectDB();
    try {
      Alert alert;
      prepareSearch1 = connect.prepareStatement(sql);
      prepareSearch1.setString(1, searchTextField.getText());
      prepareSearch1.setString(2, searchTextField.getText());
      prepareSearch1.setString(3, searchTextField.getText());
      prepareSearch1.setString(4, searchTextField.getText());
      resultSearch1 = prepareSearch1.executeQuery();

      if(searchTextField.getText().isEmpty()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy nhập thông tin user vào ô tìm kiếm");
        alert.showAndWait();
      } else if (resultSearch1.next()) {
        typeSearch = true;
        showReader();
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Không tồn tại user đang tìm");
        alert.showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize(){
    showReader();
    tableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 1) {
        selectReader();
      }
    });
  }
}