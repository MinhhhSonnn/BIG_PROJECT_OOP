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
import org.example.btl.model.BorrowingRecord;

public class BorrowHistoryController {

  @FXML
  private TableColumn<BorrowingRecord, String> ISBNColumn;

  @FXML
  private HBox addBookButton;

  @FXML
  private TableColumn<BorrowingRecord, String> bookNameColumn;

  @FXML
  private TableColumn<BorrowingRecord, LocalDate> borrowDateColumn;

  @FXML
  private HBox borrowHistoryButton;

  @FXML
  private HBox changeManagerButton;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TableColumn<BorrowingRecord, LocalDate> returnDateColumn;

  @FXML
  private TableView<BorrowingRecord> tableView;

  @FXML
  private TableColumn<BorrowingRecord, String> userNameColumn;

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

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;

  public ObservableList<BorrowingRecord> listBorrowHistory() {
    ObservableList<BorrowingRecord> returnRecord = FXCollections.observableArrayList();

    String sql = "SELECT * FROM borrowedhistory ORDER BY borrowDate DESC";

    connect = Database.connectDB();

    try {
      BorrowingRecord record;
      prepare = connect.prepareStatement(sql);
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

  @FXML
  public void initialize() {
    showRecord();
  }
}
