package com.example.library;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.example.btl.api.GoogleBooksAPI;
import org.example.btl.model.Book;
import org.junit.jupiter.api.Test;

public class GoogleBooksAPITest {

  @Test
  void testGetBookInfoWithRealAPI() {
    // Test với một query thực tế
    Book result = GoogleBooksAPI.getBookInfo("Clean Code Robert Martin");

    assertNotNull(result);
    assertNotNull(result.getBookName());
    assertNotNull(result.getAuthor());
    assertNotNull(result.getISBN());
  }

  @Test
  void testGetBooksInfoConcurrently() {
    List<String> queries = Arrays.asList(
        "Clean Code",
        "Design Patterns",
        "Refactoring"
    );

    List<Book> results = GoogleBooksAPI.getBooksInfoConcurrently(queries);

    assertNotNull(results);
    assertFalse(results.isEmpty());
    assertTrue(results.size() <= queries.size());

    // Kiểm tra mỗi book có dữ liệu hợp lệ
    results.forEach(book -> {
      assertNotNull(book.getBookName());
      assertNotNull(book.getAuthor());
      assertNotNull(book.getISBN());
    });
  }

  @Test
  void testGetBookInfoAsync() throws ExecutionException, InterruptedException {
    CompletableFuture<Book> futureBook = GoogleBooksAPI.getBookInfoAsync("Java Programming");

    Book result = futureBook.get();

    assertNotNull(result);
    assertNotNull(result.getBookName());
    assertNotNull(result.getAuthor());
    assertNotNull(result.getISBN());
  }

  @Test
  void testInvalidQuery() {
    // Test với một query không hợp lệ
    Book result = GoogleBooksAPI.getBookInfo("@#$%^&*()");
    assertNull(result);
  }

  @Test
  void testEmptyQuery() {
    Book result = GoogleBooksAPI.getBookInfo("");
    assertNull(result);
  }

  @Test
  void testNullQuery() {
    Book result = GoogleBooksAPI.getBookInfo(null);
    assertNull(result);
  }

  @Test
  void testConcurrentEmptyQueries() {
    List<String> emptyQueries = Arrays.asList();
    List<Book> results = GoogleBooksAPI.getBooksInfoConcurrently(emptyQueries);
    assertTrue(results.isEmpty());
  }

  @Test
  void testShutdown() {
    // Test shutdown không ném ra exception
    assertDoesNotThrow(() -> {
      GoogleBooksAPI.shutdown();
    });
  }
}