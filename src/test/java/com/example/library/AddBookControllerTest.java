package com.example.library;

import static org.junit.jupiter.api.Assertions.*;

import org.example.btl.Controllers.EditBookController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddBookControllerTest {

  private EditBookController controller;

  @BeforeEach
  void setUp() {
    controller = new EditBookController();
  }

  @Test
  void testIsInteger() {
    // Test các trường hợp số nguyên hợp lệ
    assertTrue(EditBookController.isInteger("123"));
    assertTrue(EditBookController.isInteger("0"));
    assertTrue(EditBookController.isInteger("-123"));

    // Test các trường hợp không hợp lệ
    assertFalse(EditBookController.isInteger("abc"));
    assertFalse(EditBookController.isInteger("12.3"));
    assertFalse(EditBookController.isInteger(""));
    assertFalse(EditBookController.isInteger(null));
  }

  @Test
  void testValidateQuantityString() {
    // Test các trường hợp hợp lệ
    assertTrue(isValidQuantity("5"));
    assertTrue(isValidQuantity("100"));
    assertTrue(isValidQuantity("2147483640")); // Max value - 7

    // Test các trường hợp không hợp lệ
    assertFalse(isValidQuantity("abc")); // Không phải số
    assertFalse(isValidQuantity("-1")); // Số âm
    assertFalse(isValidQuantity("2147483648")); // Vượt quá Integer.MAX_VALUE
    assertFalse(isValidQuantity("")); // Rỗng
    assertFalse(isValidQuantity(null)); // null
  }

  @Test
  void testValidateISBNString() {
    // Test các trường hợp hợp lệ
    assertTrue(isValidISBN("9780132350884")); // ISBN-13
    assertTrue(isValidISBN("0132350884")); // ISBN-10

    // Test các trường hợp không hợp lệ
    assertFalse(isValidISBN("")); // Rỗng
    assertFalse(isValidISBN(null)); // null
    assertFalse(isValidISBN("123")); // Quá ngắn
    assertFalse(isValidISBN("abcdefghijkl")); // Không phải số
    assertFalse(isValidISBN("12345678901234")); // Quá dài
  }

  // Helper methods
  private boolean isValidQuantity(String quantity) {
    if (!EditBookController.isInteger(quantity)) {
      return false;
    }
    try {
      int qty = Integer.parseInt(quantity);
      return qty >= 0 && qty <= 2147483640;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean isValidISBN(String isbn) {
    if (isbn == null || isbn.isEmpty()) {
      return false;
    }
    // ISBN phải là chuỗi số có độ dài 10 hoặc 13
    return isbn.matches("\\d{10}|\\d{13}");
  }
}
