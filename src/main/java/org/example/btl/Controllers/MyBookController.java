package org.example.btl.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Database.Database;
import org.example.btl.InformationUserName;
import org.example.btl.model.Book;
import org.example.btl.model.BorrowingRecord;

public class MyBookController {

  @FXML
  private TableColumn<BorrowingRecord, String> ISBNColumn;

  @FXML
  private TableColumn<BorrowingRecord, String> bookNameColumn;

  @FXML
  private TableColumn<BorrowingRecord, LocalDate> borrowDateColumn;

  @FXML
  private HBox dashboardUserButton;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox myBookButton;

  @FXML
  private TableColumn<BorrowingRecord, LocalDate> returnDateColumn;

  @FXML
  private TableView<BorrowingRecord> tableView;

  @FXML
  private TableColumn<BorrowingRecord, String> userNameColumn;

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

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

  public ObservableList<BorrowingRecord> listBorrowHistory() {
    ObservableList<BorrowingRecord> returnRecord = FXCollections.observableArrayList();

    String sql = "SELECT * FROM borrowedhistory WHERE userName = ? ORDER BY borrowDate DESC";

    connect = Database.connectDB();

    try {
      BorrowingRecord record;
      prepare = connect.prepareStatement(sql);
      prepare.setString(1, InformationUserName.userName);
      result = prepare.executeQuery();
      while (result.next()) {
        LocalDate localDate = null;
        if (result.getDate("returnDate") != null) {
          localDate = result.getDate("returnDate").toLocalDate();
        }
        record = new BorrowingRecord(result.getString("ISBN"), result.getString("bookName"),
            result.getString("userName"), result.getDate("borrowDate").toLocalDate(), localDate);

        returnRecord.add(record);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnRecord;
  }

  ObservableList<BorrowingRecord> records;

  public void showRecord() {
    records = listBorrowHistory();

    bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
    borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
    returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

    tableView.setItems(records);
  }

  private PreparedStatement prepareFind;
  private ResultSet resultFind;

  public void selectRecord() {
    Book book = null;
    BorrowingRecord record = tableView.getSelectionModel().getSelectedItem();
    int index = tableView.getSelectionModel().getFocusedIndex();
    if (index < 0) {
      return;
    }
    try {
      if (record == null) {
        return;
      }
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/org/example/btl/DetailReturnBook.fxml"));
      Parent root = loader.load();

      String sql = "SELECT * FROM books WHERE ISBN = ?";

      connect = Database.connectDB();
      try {
        prepareFind = connect.prepareStatement(sql);
        prepareFind.setString(1, record.getISBN());
        resultFind = prepareFind.executeQuery();
        resultFind.next();
        book = new Book(resultFind.getString("bookName"), resultFind.getString("author"),
            resultFind.getString("ISBN"), resultFind.getInt("quantity"),
            resultFind.getString("description"), resultFind.getString("imageUrl"),
            resultFind.getString("publicationYear"), resultFind.getString("category"));
      } catch (Exception e) {
        e.printStackTrace();
      }
      DetailReturnBookController controller = loader.getController();

      controller.setData(book, record);

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
  public void initialize() {
    showRecord();
    tableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 1) {
        selectRecord();
      }
    });
  }
}
