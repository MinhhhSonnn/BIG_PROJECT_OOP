package org.example.btl.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.example.btl.Database.BookDAO;
import org.example.btl.api.GoogleBooksAPI;
import org.example.btl.Alert.AlertUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookService {

  private final BookDAO bookDAO;
  private final ExecutorService executor;

  public BookService() {
    this.bookDAO = new BookDAO();
    this.executor = Executors.newFixedThreadPool(2);
  }

  public void addBook(String isbn, int quantity, AddBookCallback callback) {
    CompletableFuture.supplyAsync(() -> GoogleBooksAPI.getBookInfo(isbn), executor)
        .thenAcceptAsync(book -> {
          if (book == null) {
            callback.onComplete(false, "ISBN không hợp lệ");
            return;
          }

          boolean success = bookDAO.insertBook(book, quantity);
          if (success) {
            callback.onComplete(true, "Thêm sách thành công");
          } else {
            callback.onComplete(false, "Sách đã tồn tại trong kho");
          }
        }, executor)
        .exceptionally(ex -> {
          ex.printStackTrace();
          callback.onComplete(false, "Lỗi khi thêm sách");
          return null;
        });
  }

  public void updateBook(String isbn, String bookName, String author, String category,
      String quantityStr, String publicationYear, String description) {
    if (isbn.isEmpty()) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Hãy nhập ISBN để thay đổi thông tin sách");
      return;
    }

    CompletableFuture.runAsync(() -> {
      boolean exists = bookDAO.findBookByISBN(isbn) != null;
      if (!exists) {
        Platform.runLater(() -> AlertUtil.showAlert(Alert.AlertType.ERROR, "ISBN không có trong thư viện"));
        return;
      }

      int quantity = -1;
      if (!quantityStr.isEmpty()) {
        try {
          quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
          Platform.runLater(() -> AlertUtil.showAlert(Alert.AlertType.ERROR, "Số lượng không hợp lệ"));
          return;
        }
      }

      boolean updated = bookDAO.updateBook(isbn, bookName, author, category, quantity, publicationYear, description);
      Platform.runLater(() -> {
        if (updated) {
          AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Cập nhật sách thành công");
        } else {
          AlertUtil.showAlert(Alert.AlertType.ERROR, "Cập nhật sách thất bại");
        }
      });
    }, executor).exceptionally(ex -> {
      ex.printStackTrace();
      Platform.runLater(() -> AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi khi cập nhật sách..."));
      return null;
    });
  }

  public void deleteBook(String isbn) {
    if (isbn.isEmpty()) {
      AlertUtil.showAlert(Alert.AlertType.ERROR, "Hãy nhập ISBN để xoá thông tin sách");
      return;
    }

    CompletableFuture.runAsync(() -> {
      boolean exists = bookDAO.findBookByISBN(isbn) != null;
      if (!exists) {
        Platform.runLater(() -> AlertUtil.showAlert(Alert.AlertType.ERROR, "ISBN không có trong thư viện"));
        return;
      }

      boolean deleted = bookDAO.deleteBook(isbn);
      Platform.runLater(() -> {
        if (deleted) {
          AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Xoá sách thành công");
        } else {
          AlertUtil.showAlert(Alert.AlertType.ERROR, "Xoá sách thất bại");
        }
      });
    }, executor).exceptionally(ex -> {
      ex.printStackTrace();
      Platform.runLater(() -> AlertUtil.showAlert(Alert.AlertType.ERROR, "Lỗi khi xoá sách..."));
      return null;
    });
  }

  public void shutdown() {
    executor.shutdown();
    bookDAO.close();
  }

  public interface AddBookCallback {
    void onComplete(boolean success, String message);
  }
}
