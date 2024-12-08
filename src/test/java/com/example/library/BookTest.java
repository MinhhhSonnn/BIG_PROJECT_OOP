package com.example.library;

import static org.junit.jupiter.api.Assertions.*;

import org.example.btl.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import com.google.zxing.WriterException;

public class BookTest {
  private Book book;
  private final String TEST_BOOK_NAME = "Clean Code";
  private final String TEST_AUTHOR = "Robert C. Martin";
  private final String TEST_ISBN = "9780132350884";
  private final String TEST_DESCRIPTION = "A handbook of agile software craftsmanship";
  private final String TEST_IMAGE_URL = "example.com/image.jpg";
  private final String TEST_PUBLICATION_YEAR = "2008";
  private final String TEST_CATEGORY = "Programming";
  private final int TEST_QUANTITY = 5;

  @BeforeEach
  void setUp() {
    book = new Book(
        TEST_BOOK_NAME,
        TEST_AUTHOR,
        TEST_ISBN,
        TEST_QUANTITY,
        TEST_DESCRIPTION,
        TEST_IMAGE_URL,
        TEST_PUBLICATION_YEAR,
        TEST_CATEGORY
    );
  }

  @Test
  void testDefaultConstructor() {
    Book emptyBook = new Book();
    assertEquals("", emptyBook.getBookName());
    assertEquals("", emptyBook.getAuthor());
    assertEquals("", emptyBook.getISBN());
    assertEquals("", emptyBook.getDescription());
    assertEquals(0, emptyBook.getQuantity());
    assertNull(emptyBook.getImageUrl());
    assertEquals("", emptyBook.getPublicationYear());
    assertEquals("", emptyBook.getCategory());
  }

  @Test
  void testGettersAndSetters() {
    // Test initial values
    assertEquals(TEST_BOOK_NAME, book.getBookName());
    assertEquals(TEST_AUTHOR, book.getAuthor());
    assertEquals(TEST_ISBN, book.getISBN());
    assertEquals(TEST_DESCRIPTION, book.getDescription());
    assertEquals(TEST_QUANTITY, book.getQuantity());
    assertEquals(TEST_IMAGE_URL, book.getImageUrl());
    assertEquals(TEST_PUBLICATION_YEAR, book.getPublicationYear());
    assertEquals(TEST_CATEGORY, book.getCategory());

    // Test setters
    book.setBookName("New Book Name");
    assertEquals("New Book Name", book.getBookName());

    book.setAuthor("New Author");
    assertEquals("New Author", book.getAuthor());

    book.setISBN("1234567890");
    assertEquals("1234567890", book.getISBN());

    book.setDescription("New Description");
    assertEquals("New Description", book.getDescription());

    book.setQuantity(10);
    assertEquals(10, book.getQuantity());

    book.setImageUrl("new-image-url");
    assertEquals("new-image-url", book.getImageUrl());

    book.setPublicationYear("2024");
    assertEquals("2024", book.getPublicationYear());

    book.setCategory("New Category");
    assertEquals("New Category", book.getCategory());
  }

  @Test
  void testGenerateQRCode() {
    // Tạo đường dẫn với thư mục cha
    String directoryPath = "qrcodes"; // thư mục để lưu QR code
    String fileName = "test-qr-code.png";
    String outputPath = directoryPath + File.separator + fileName;

    try {
      // Tạo thư mục nếu chưa tồn tại
      new File(directoryPath).mkdirs();

      book.generateQRCode(outputPath);
      File qrCodeFile = new File(outputPath);

      // Kiểm tra xem file QR code đã được tạo thành công
      assertTrue(qrCodeFile.exists());
      assertTrue(qrCodeFile.length() > 0);

      // Dọn dẹp file test
      qrCodeFile.delete();
      new File(directoryPath).delete(); // Xóa cả thư mục test
    } catch (WriterException | IOException e) {
      fail("QR code generation failed: " + e.getMessage());
    }
  }
}