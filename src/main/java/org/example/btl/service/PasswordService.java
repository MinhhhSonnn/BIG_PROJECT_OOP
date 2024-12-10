package org.example.btl.service;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.example.btl.Util.EmailUtil;
import org.example.btl.Database.Database;
import java.sql.*;

public class PasswordService {
  private String resetCode;
  private static final int CODE_LENGTH = 6;
  public void sendExistingPassword(String email) {
    // check email ton tai trong he thong khong
    if (!isEmailExists(email)) {
      throw new RuntimeException("Email không tồn tại trong hệ thống!");
    }
    resetCode = generateResetCode();
    EmailUtil.sendPasswordResetEmail(email, resetCode);
  }
  public String getResetCode() {
    return resetCode;
  }

  private boolean isEmailExists(String email) {
    String sql = "SELECT COUNT(*) FROM account WHERE email = ?";

    try (Connection connect = Database.connectDB();
        PreparedStatement prepare = connect.prepareStatement(sql)) {

      prepare.setString(1, email);

      try (ResultSet rs = prepare.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Lỗi khi kiểm tra email");
    }
    return false;
  }
  private String generateResetCode() {
    SecureRandom random = new SecureRandom();
    int code = 100000 + random.nextInt(900000);
    return String.valueOf(code);
  }



}
