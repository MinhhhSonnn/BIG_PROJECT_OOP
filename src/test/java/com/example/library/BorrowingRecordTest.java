package com.example.library;

import static org.junit.jupiter.api.Assertions.*;

import org.example.btl.model.BorrowingRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class BorrowingRecordTest {
  private BorrowingRecord borrowingRecord;
  private final String TEST_ISBN = "9780132350884";
  private final String TEST_BOOK_NAME = "Clean Code";
  private final String TEST_USER_NAME = "johnDoe";
  private final LocalDate TEST_BORROW_DATE = LocalDate.of(2024, 12, 1);
  private final LocalDate TEST_RETURN_DATE = LocalDate.of(2024, 12, 15);

  @BeforeEach
  void setUp() {
    borrowingRecord = new BorrowingRecord(
        TEST_ISBN,
        TEST_BOOK_NAME,
        TEST_USER_NAME,
        TEST_BORROW_DATE,
        TEST_RETURN_DATE
    );
  }

  @Test
  void testDefaultConstructor() {
    BorrowingRecord defaultRecord = new BorrowingRecord();
    assertNull(defaultRecord.getISBN());
    assertNull(defaultRecord.getBookName());
    assertNull(defaultRecord.getUserName());
    assertNull(defaultRecord.getBorrowDate());
    assertNull(defaultRecord.getReturnDate());
  }

  @Test
  void testParameterizedConstructor() {
    assertEquals(TEST_ISBN, borrowingRecord.getISBN());
    assertEquals(TEST_BOOK_NAME, borrowingRecord.getBookName());
    assertEquals(TEST_USER_NAME, borrowingRecord.getUserName());
    assertEquals(TEST_BORROW_DATE, borrowingRecord.getBorrowDate());
    assertEquals(TEST_RETURN_DATE, borrowingRecord.getReturnDate());
  }

  @Test
  void testSetAndGetISBN() {
    String newISBN = "9780134494166";
    borrowingRecord.setISBN(newISBN);
    assertEquals(newISBN, borrowingRecord.getISBN());
  }

  @Test
  void testSetAndGetBookName() {
    String newBookName = "Clean Architecture";
    borrowingRecord.setBookName(newBookName);
    assertEquals(newBookName, borrowingRecord.getBookName());
  }

  @Test
  void testSetAndGetUserName() {
    String newUserName = "janeDoe";
    borrowingRecord.setUserName(newUserName);
    assertEquals(newUserName, borrowingRecord.getUserName());
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
    borrowingRecord.setISBN(null);
    borrowingRecord.setBookName(null);
    borrowingRecord.setUserName(null);
    borrowingRecord.setBorrowDate(null);
    borrowingRecord.setReturnDate(null);

    assertNull(borrowingRecord.getISBN());
    assertNull(borrowingRecord.getBookName());
    assertNull(borrowingRecord.getUserName());
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

  @Test
  void testEmptyStrings() {
    borrowingRecord.setISBN("");
    borrowingRecord.setBookName("");
    borrowingRecord.setUserName("");

    assertEquals("", borrowingRecord.getISBN());
    assertEquals("", borrowingRecord.getBookName());
    assertEquals("", borrowingRecord.getUserName());
  }
}