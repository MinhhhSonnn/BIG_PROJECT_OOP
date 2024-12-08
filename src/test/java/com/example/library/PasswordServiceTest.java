package com.example.library;

import org.example.btl.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordServiceTest {
  private PasswordService passwordService;

  @BeforeEach
  void setUp() {
    passwordService = new PasswordService();
  }

  @Test
  void testGenerateResetCode() {
    // Gọi sendExistingPassword với email hợp lệ để trigger generateResetCode
    String testEmail = "sonhoctap59@gmail.com";
    passwordService.sendExistingPassword(testEmail);

    // Lấy mã reset được tạo
    String resetCode = passwordService.getResetCode();

    // Kiểm tra độ dài của mã
    assertEquals(6, resetCode.length());

    // Kiểm tra mã có phải là số không
    assertTrue(resetCode.matches("\\d+"));

    // Kiểm tra giá trị nằm trong khoảng cho phép
    int codeValue = Integer.parseInt(resetCode);
    assertTrue(codeValue >= 100000 && codeValue <= 999999);
  }

  @Test
  void testSendExistingPasswordWithValidEmail() {
    String validEmail = "sonhoctap59@gmail.com"; // Giả sử email này tồn tại trong DB

    assertDoesNotThrow(() -> {
      passwordService.sendExistingPassword(validEmail);
    });

    // Kiểm tra mã reset đã được tạo
    assertNotNull(passwordService.getResetCode());
  }

  @Test
  void testSendExistingPasswordWithInvalidEmail() {
    String invalidEmail = "nonexistent@example.com"; // Email không tồn tại

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      passwordService.sendExistingPassword(invalidEmail);
    });

    assertEquals("Email không tồn tại trong hệ thống!", exception.getMessage());
  }

  @Test
  void testMultipleResetCodesAreDifferent() {
    String testEmail = "sonhoctap59@gmail.com"; // Giả sử email này tồn tại trong DB

    // Tạo mã reset lần 1
    passwordService.sendExistingPassword(testEmail);
    String firstCode = passwordService.getResetCode();

    // Tạo mã reset lần 2
    passwordService.sendExistingPassword(testEmail);
    String secondCode = passwordService.getResetCode();

    // Kiểm tra 2 mã khác nhau
    assertNotEquals(firstCode, secondCode);
  }
}
