package org.example.btl.Database;

import org.example.btl.model.Book;

import java.sql.*;

public class BookDAO {

  private Connection connection;

  public BookDAO() {
    this.connection = database.connectDB();
  }

  public boolean insertBook(Book book, int quantity) {
    String sql = "INSERT INTO books (bookName, author, category, quantity, publicationYear, ISBN, imageUrl, description) " +
        "SELECT ?, ?, ?, ?, ?, ?, ?, ? " +
        "WHERE NOT EXISTS (SELECT 1 FROM books WHERE ISBN = ?);";

    try (PreparedStatement prepare = connection.prepareStatement(sql)) {
      prepare.setString(1, book.getBookName());
      prepare.setString(2, book.getAuthor());
      prepare.setString(3, book.getCategory());
      prepare.setInt(4, quantity);
      prepare.setString(5, book.getPublicationYear());
      prepare.setString(6, book.getISBN());
      prepare.setString(7, book.getImageUrl());
      prepare.setString(8, book.getDescription());
      prepare.setString(9, book.getISBN());

      int rowsAffected = prepare.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public Book findBookByISBN(String isbn) {
    String sql = "SELECT * FROM books WHERE ISBN = ?";

    try (PreparedStatement prepare = connection.prepareStatement(sql)) {
      prepare.setString(1, isbn);
      ResultSet result = prepare.executeQuery();

      if (result.next()) {
        return new Book(
            result.getString("bookName"),
            result.getString("author"),
            result.getString("category"),
            result.getInt("quantity"),
            result.getString("publicationYear"),
            result.getString("ISBN"),
            result.getString("imageUrl"),
            result.getString("description")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }


  public boolean updateBook(String isbn, String bookName, String author, String category,
      int quantity, String publicationYear, String description) {
    String sql = "UPDATE books SET " +
        "bookName = CASE WHEN ? != '' THEN ? ELSE bookName END, " +
        "author = CASE WHEN ? != '' THEN ? ELSE author END, " +
        "category = CASE WHEN ? != '' THEN ? ELSE category END, " +
        "quantity = CASE WHEN ? != -1 THEN ? ELSE quantity END, " +
        "publicationYear = CASE WHEN ? != '' THEN ? ELSE publicationYear END, " +
        "description = CASE WHEN ? != '' THEN ? ELSE description END " +
        "WHERE ISBN = ?";

    try (PreparedStatement prepare = connection.prepareStatement(sql)) {
      prepare.setString(1, bookName);
      prepare.setString(2, bookName);
      prepare.setString(3, author);
      prepare.setString(4, author);
      prepare.setString(5, category);
      prepare.setString(6, category);
      prepare.setInt(7, quantity);
      prepare.setInt(8, quantity);
      prepare.setString(9, publicationYear);
      prepare.setString(10, publicationYear);
      prepare.setString(11, description);
      prepare.setString(12, description);
      prepare.setString(13, isbn);

      int rowsAffected = prepare.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteBook(String isbn) {
    String sql = "DELETE FROM books WHERE ISBN = ?";

    try (PreparedStatement prepare = connection.prepareStatement(sql)) {
      prepare.setString(1, isbn);
      int rowsAffected = prepare.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void close() {
    try {
      if(connection != null && !connection.isClosed()){
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
