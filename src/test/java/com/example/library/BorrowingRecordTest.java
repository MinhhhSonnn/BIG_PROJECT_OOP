package com.example.library;

import static org.junit.jupiter.api.Assertions.*;

import org.example.btl.model.BorrowingRecord;
import org.example.btl.model.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class BorrowingRecordTest {
  private BorrowingRecord borrowingRecord;
  private Reader testReader;
  private final String TEST_ISBN = "9780132350884";
  private final String TEST_BOOK_NAME = "Clean Code";
  private final LocalDate TEST_BORROW_DATE = LocalDate.of(2024, 12, 1);
  private final LocalDate TEST_RETURN_DATE = LocalDate.of(2024, 12, 15);

  @BeforeEach
  void setUp() {
    testReader = new Reader("testUser", "password123", "test@example.com", 0, 0);
    borrowingRecord = new BorrowingRecord(
        TEST_ISBN,
        TEST_BOOK_NAME,
        testReader,
        TEST_BORROW_DATE,
        null,
        TEST_RETURN_DATE
    );
  }

  @Test
  void testDefaultConstructor() {
    BorrowingRecord defaultRecord = new BorrowingRecord();
    assertNull(defaultRecord.getIsbn());
    assertNull(defaultRecord.getBookName());
    assertNull(defaultRecord.getReader());
    assertNull(defaultRecord.getBorrowDate());
    assertNull(defaultRecord.getReturnDate());
  }

  @Test
  void testParameterizedConstructor() {
    assertEquals(TEST_ISBN, borrowingRecord.getIsbn());
    assertEquals(TEST_BOOK_NAME, borrowingRecord.getBookName());
    assertEquals(testReader, borrowingRecord.getReader());
    assertEquals(TEST_BORROW_DATE, borrowingRecord.getBorrowDate());
    assertEquals(TEST_RETURN_DATE, borrowingRecord.getReturnDate());
  }

  @Test
  void testSetAndGetIsbn() {
    String newIsbn = "9780134494166";
    borrowingRecord.setIsbn(newIsbn);
    assertEquals(newIsbn, borrowingRecord.getIsbn());
  }

  @Test
  void testSetAndGetBookName() {
    String newBookName = "Clean Architecture";
    borrowingRecord.setBookName(newBookName);
    assertEquals(newBookName, borrowingRecord.getBookName());
  }

  @Test
  void testSetAndGetReader() {
    Reader newReader = new Reader("newUser", "newPass", "new@example.com", 1, 0);
    borrowingRecord.setReader(newReader);
    assertEquals(newReader, borrowingRecord.getReader());
  }

  @Test
  void testSetAndGetBorrowDate() {
    LocalDate newBorrowDate = LocalDate.of(2024, 12, 5);
    borrowingRecord.setBorrowDate(newBorrowDate);
    assertEquals(newBorrowDate, borrowingRecord.getBorrowDate());
  }

  @Test
  void testSetAndGetReturnDate() {
    LocalDate newReturnDate = LocalDate.of(2024, 12, 20);
    borrowingRecord.setReturnDate(newReturnDate);
    assertEquals(newReturnDate, borrowingRecord.getReturnDate());
  }

  @Test
  void testNullValues() {
    borrowingRecord.setIsbn(null);
    borrowingRecord.setBookName(null);
    borrowingRecord.setReader(null);
    borrowingRecord.setBorrowDate(null);
    borrowingRecord.setReturnDate(null);

    assertNull(borrowingRecord.getIsbn());
    assertNull(borrowingRecord.getBookName());
    assertNull(borrowingRecord.getReader());
    assertNull(borrowingRecord.getBorrowDate());
    assertNull(borrowingRecord.getReturnDate());
  }

  @Test
  void testReturnDateAfterBorrowDate() {
    LocalDate borrowDate = LocalDate.of(2024, 12, 1);
    LocalDate returnDate = LocalDate.of(2024, 12, 15);

    borrowingRecord.setBorrowDate(borrowDate);
    borrowingRecord.setReturnDate(returnDate);

    assertTrue(borrowingRecord.getReturnDate().isAfter(borrowingRecord.getBorrowDate()));
  }
}