package com.example.library;

import static org.junit.jupiter.api.Assertions.*;

import org.example.btl.model.Reader;
import org.example.btl.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderTest {
  private Reader reader;
  private final String TEST_USERNAME = "testReader";
  private final String TEST_PASSWORD = "password123";
  private final String TEST_EMAIL = "reader@test.com";
  private final int TEST_BOOKS_BORROW = 2;
  private final int TEST_VIOLATIONS = 1;

  @BeforeEach
  void setUp() {
    reader = new Reader(
        TEST_USERNAME,
        TEST_PASSWORD,
        TEST_EMAIL,
        TEST_BOOKS_BORROW,
        TEST_VIOLATIONS
    );
  }

  @Test
  void testDefaultConstructor() {
    Reader defaultReader = new Reader();
    assertEquals(0, defaultReader.getNumberOfBooksBorrow());
    assertEquals(0, defaultReader.getNumberOfViolations());
    assertNotNull(defaultReader.getUserName());
    assertNotNull(defaultReader.getPassword());
    assertNotNull(defaultReader.getEmail());
  }

  @Test
  void testParameterizedConstructor() {
    assertEquals(TEST_USERNAME, reader.getUserName());
    assertEquals(TEST_PASSWORD, reader.getPassword());
    assertEquals(TEST_EMAIL, reader.getEmail());
    assertEquals(TEST_BOOKS_BORROW, reader.getNumberOfBooksBorrow());
    assertEquals(TEST_VIOLATIONS, reader.getNumberOfViolations());
  }

  @Test
  void testGetRole() {
    assertEquals("Reader", reader.getRole());
  }

  @Test
  void testSetAndGetNumberOfBooksBorrow() {
    int newBooksCount = 5;
    reader.setNumberOfBooksBorrow(newBooksCount);
    assertEquals(newBooksCount, reader.getNumberOfBooksBorrow());
  }

  @Test
  void testSetAndGetNumberOfViolations() {
    int newViolations = 3;
    reader.setNumberOfViolations(newViolations);
    assertEquals(newViolations, reader.getNumberOfViolations());
  }

  @Test
  void testInheritanceFromUser() {
    assertTrue(reader instanceof User);
  }

  @Test
  void testSetAndGetUserName() {
    String newUsername = "newReader";
    reader.setUserName(newUsername);
    assertEquals(newUsername, reader.getUserName());
  }

  @Test
  void testSetAndGetPassword() {
    String newPassword = "newPassword123";
    reader.setPassword(newPassword);
    assertEquals(newPassword, reader.getPassword());
  }

  @Test
  void testSetAndGetEmail() {
    String newEmail = "newreader@test.com";
    reader.setEmail(newEmail);
    assertEquals(newEmail, reader.getEmail());
  }

  @Test
  void testNegativeNumberOfBooksBorrow() {
    assertThrows(IllegalArgumentException.class, () -> {
      reader.setNumberOfBooksBorrow(-1);
    });
  }

  @Test
  void testNegativeNumberOfViolations() {
    assertThrows(IllegalArgumentException.class, () -> {
      reader.setNumberOfViolations(-1);
    });
  }
}