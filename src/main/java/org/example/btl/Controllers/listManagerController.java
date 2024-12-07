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
import org.example.btl.model.Manager;

public class listManagerController {

  @FXML
  private TableColumn<Manager, Integer> IDColumn;

  @FXML
  private HBox addBookButton;

  @FXML
  private HBox borrowHistoryButton;

  @FXML
  private HBox changeManagerButton;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private TableColumn<Manager, String> emailColumn;

  @FXML
  private TableColumn<Manager, String> employeeNameColumn;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TableColumn<Manager, String> phoneColumn;

  @FXML
  private TableColumn<Manager, String> positionColumn;

  @FXML
  private ImageView searchButton;

  @FXML
  private TextField searchTextField;

  @FXML
  private TableColumn<Manager, String> sexColumn;

  @FXML
  private TableView<Manager> tableView;

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

  // kiem tra quantity co phai int hay k
  public static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private PreparedStatement prepareSearch2;
  private ResultSet resultSearch2;

  public ObservableList<Manager> listEmployeeSearch(){
    ObservableList<Manager> returnEmployee = FXCollections.observableArrayList();

    String sql = "SELECT * "
        + "FROM employees "
        + "WHERE ID = ? "
        + "   OR employeeName = ? "
        + "   OR sex = ? "
        + "   OR position = ? "
        + "   OR phone = ? "
        + "   OR email = ?;";

    connect = database.connectDB();

    try {
      Manager employee;
      int ID = -1;
      if(isInteger(searchTextField.getText())){
        ID = Integer.parseInt(searchTextField.getText());
      }

      prepareSearch2 = connect.prepareStatement(sql);
      prepareSearch2.setInt(1, ID);
      prepareSearch2.setString(2, searchTextField.getText());
      prepareSearch2.setString(3, searchTextField.getText());
      prepareSearch2.setString(4, searchTextField.getText());
      prepareSearch2.setString(5, searchTextField.getText());
      prepareSearch2.setString(6, searchTextField.getText());
      resultSearch2 = prepareSearch2.executeQuery();
      while(resultSearch2.next()){
        employee = new Manager(resultSearch2.getInt("ID"), resultSearch2.getString("employeeName"), resultSearch2.getString("sex"), resultSearch2.getString("position"), resultSearch2.getString("phone"), resultSearch2.getString("email"));

        returnEmployee.add(employee);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnEmployee;
  }

  public ObservableList<Manager> listEmployeeDefault(){
    ObservableList<Manager> returnEmployee = FXCollections.observableArrayList();

    String sql = "SELECT * FROM employees";

    connect = database.connectDB();

    try {
      Manager employee;
      prepareDefault = connect.prepareStatement(sql);
      resultDefault = prepareDefault.executeQuery();
      while(resultDefault.next()){
        employee = new Manager(resultDefault.getInt("ID"), resultDefault.getString("employeeName"), resultDefault.getString("sex"), resultDefault.getString("position"), resultDefault.getString("phone"), resultDefault.getString("email"));

        returnEmployee.add(employee);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return returnEmployee;
  }

  boolean typeSearch = false;
  /*
  false la mac dinh
  true la hien thi theo search
  */
  ObservableList<Manager> employees;
  public void showEmployee() {
    if (typeSearch == false) {
      employees = listEmployeeDefault();
    } else {
      employees = listEmployeeSearch();
    }

    IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
    employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
    sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
    positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
    phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    tableView.setItems(employees);
  }

  private PreparedStatement prepareSearch1;
  private ResultSet resultSearch1;

  public void search(){
    String sql = "SELECT * "
        + "FROM employees "
        + "WHERE ID = ? "
        + "   OR employeeName = ? "
        + "   OR sex = ? "
        + "   OR position = ? "
        + "   OR phone = ? "
        + "   OR email = ?;";

    connect = database.connectDB();
    try {
      Alert alert;
      int ID = -1;
      if(isInteger(searchTextField.getText())){
        ID = Integer.parseInt(searchTextField.getText());
      }

      prepareSearch1 = connect.prepareStatement(sql);
      prepareSearch1.setInt(1, ID);
      prepareSearch1.setString(2, searchTextField.getText());
      prepareSearch1.setString(3, searchTextField.getText());
      prepareSearch1.setString(4, searchTextField.getText());
      prepareSearch1.setString(5, searchTextField.getText());
      prepareSearch1.setString(6, searchTextField.getText());
      resultSearch1 = prepareSearch1.executeQuery();

      if(searchTextField.getText().isEmpty()){
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Hãy nhập thông tin nhân viên vào ô tìm kiếm");
        alert.showAndWait();
      } else if (resultSearch1.next()) {
        typeSearch = true;
        showEmployee();
      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Admin Message");
        alert.setHeaderText(null);
        alert.setContentText("Không tồn tại nhân viên đang tìm");
        alert.showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @FXML
  public void initialize(){
    showEmployee();
  }
}
