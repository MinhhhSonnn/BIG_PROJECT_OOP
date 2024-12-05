package org.example.btl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import org.example.btl.Controllers.EmailUtil;
import org.example.btl.Database.database;
import java.sql.*;

public class PasswordService {

  public void sendExistingPassword(String email) {
    // Kiểm tra xem email có tồn tại trong hệ thống không
    if (!isEmailExists(email)) {
      throw new RuntimeException("Email không tồn tại trong hệ thống!");
    }

    // Lấy mật khẩu hiện tại từ database
    String currentPassword = getCurrentPasswordFromDatabase(email);

    // Gửi email chứa mật khẩu hiện tại
    EmailUtil.sendPasswordResetEmail(email, currentPassword);
  }

  private boolean isEmailExists(String email) {
    String sql = "SELECT COUNT(*) FROM account WHERE email = ?";

    try (Connection connect = database.connectDB();
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

  private String getCurrentPasswordFromDatabase(String email) {
    String sql = "SELECT password FROM account WHERE email = ?";
    try (Connection connect = database.connectDB();
        PreparedStatement prepare = connect.prepareStatement(sql)) {
      prepare.setString(1, email);

      try (ResultSet rs = prepare.executeQuery()) {
        if (rs.next()) {
          return rs.getString("password");
        } else {
          throw new RuntimeException("Không tìm thấy thông tin người dùng");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Lỗi khi kiểm tra email");
    }

  }


}
