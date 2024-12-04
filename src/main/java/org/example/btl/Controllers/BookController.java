package org.example.btl.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.btl.Alert.AlertUtil;


import java.io.IOException;
import java.util.Objects;
import org.example.btl.service.BookService;

public class BookController {

  @FXML
  private TextField ISBNNewTextField;

  @FXML
  private TextField ISBNOldTextField;

  @FXML
  private TextField ISBNTextField;

  @FXML
  private Button addButton;

  @FXML
  private TextField authorNewTextField;

  @FXML
  private TextField bookNameNewTextField;

  @FXML
  private TextField categoryNewTextField;

  @FXML
  private HBox dashboardManagerButton;

  @FXML
  private Button deleteButton;

  @FXML
  private TextField descriptionNewTextField;

  @FXML
  private HBox listBookButton;

  @FXML
  private HBox listManagerButton;

  @FXML
  private HBox listUserButton;

  @FXML
  private TextField publicationYearNewTextField;

  @FXML
  private TextField quantityNewTextField;

  @FXML
  private TextField quantityTextField;

  @FXML
  private Button updateButton;

  private BookService bookService;

  @FXML
  public void initialize() {
    bookService = new BookService();

    // Khởi tạo các sự kiện cho các nút
    dashboardManagerButton.setOnMouseClicked(event -> dashboardManagerView());
    listManagerButton.setOnMouseClicked(event -> listManagerView());
    listUserButton.setOnMouseClicked(event -> listUserView());
    listBookButton.setOnMouseClicked(event -> listBookView());

    addButton.setOnAction(event -> handleAddBook());
    updateButton.setOnAction(event -> handleUpdateBook());
    deleteButton.setOnAction(event -> handleDeleteBook());
  }
  public void dashboardManagerView() {
    loadView("/org/example/btl/dashboardManager.fxml");
  }

  public void listManagerView() {
    loadView("/org/example/btl/listManager.fxml");
  }

  public void listUserView() {
    loadView("/org/example/btl/listUser.fxml");
  }

  public void listBookView() {
    loadView("/org/example/btl/listBook.fxml");
  }

  private void loadView(String fxmlPath) {
    try {
      Stage stage = (Stage) dashboardManagerButton.getScene().getWindow();
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
      Scene scene = new Scene(root);
      stage.setResizable(false);
      stage.setTitle("UET Library Management");
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Không thể tải giao diện");
    }
  }

  private void handleAddBook() {
    String isbn = ISBNTextField.getText();
    String quantity = quantityTextField.getText();

    if (isbn.isEmpty() || quantity.isEmpty()) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Hãy điền vào ô còn trống");
      return;
    } else if (!ValidationUtil.isInteger(quantity) || Integer.parseInt(quantity) < 0 || Integer.parseInt(quantity) > 254) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Số lượng không hợp lệ");
      return;
    }

    bookService.addBook(isbn, Integer.parseInt(quantity), this::showAddBookResult);
  }

  private void showAddBookResult(boolean success, String message) {
    Platform.runLater(() -> {
      Alert.AlertType type = success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR;
      AlertUtil.showAlert(type, message);
    });
  }

  private void handleUpdateBook() {
    bookService.updateBook(
        ISBNNewTextField.getText(),
        bookNameNewTextField.getText(),
        authorNewTextField.getText(),
        categoryNewTextField.getText(),
        quantityNewTextField.getText(),
        publicationYearNewTextField.getText(),
        descriptionNewTextField.getText()
    );
  }

  private void handleDeleteBook() {
    bookService.deleteBook(ISBNOldTextField.getText());
  }


  // Cleanup
  public void cleanup() {
    bookService.shutdown();
  }
}
