package org.example.btl.Controllers;

import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
  // tai khoan gui mail
  private static final String SENDER_EMAIL = "bsdkk6@gmail.com";

  private static final String SENDER_PASSWORD = "ohff sdmj afgr psme";

  public static void sendPasswordResetEmail(String recipientEmail, String newPassword) {
    // Cấu hình Properties cho Gmail SMTP
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
      }
    });
    try {
      // Tạo message
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(SENDER_EMAIL));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
      message.setSubject("Hệ thống Quản lý Thư viện");

      // Nội dung email
      String emailContent = String.format(
          "Xin chào,\n\n" +
              "Đây là email mật khẩu của bạn từ Hệ thống Quản lý Thư viện.\n" +
              "Mật khẩu của bạn là: %s\n\n" +
              "Trân trọng,\n" +
              "Hệ thống Quản lý Thư viện", newPassword);

      message.setText(emailContent);

      // Gửi email
      Transport.send(message);

      System.out.println("Email đã được gửi thành công!");

    } catch (MessagingException e) {
      throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
    }
  }
}


