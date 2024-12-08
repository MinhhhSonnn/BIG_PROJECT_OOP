package com.example.library;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import org.example.btl.Controllers.signUpController;
import org.example.btl.Database.database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
class SignUpControllerTest {

  @InjectMocks
  private signUpController controller;

  @Mock
  private Connection mockConnection;

  @Mock
  private PreparedStatement mockPreparedStatement;

  @Mock
  private TextField usernameTextField;

  @Mock
  private TextField passwordTextField;

  @Mock
  private TextField confirmPasswordTextField;

  @Mock
  private TextField emailTextField;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);

    // Mock database connection
    try (var mockedStatic = mockStatic(database.class)) {
      mockedStatic.when(database::connectDB).thenReturn(mockConnection);
    }

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

    // Inject mocked text fields
    controller.setUsername(usernameTextField.getText());
    controller.setPassword(passwordTextField.getText());
    controller.setConfirmPassword(confirmPasswordTextField.getText());
    controller.setEmail(emailTextField.getText());
  }

  @Test
  void testSignUpWithValidData() throws Exception {
    // Arrange
    when(usernameTextField.getText()).thenReturn("testUser");
    when(passwordTextField.getText()).thenReturn("password123");
    when(confirmPasswordTextField.getText()).thenReturn("password123");
    when(emailTextField.getText()).thenReturn("test@example.com");
    when(mockPreparedStatement.executeUpdate()).thenReturn(1);

    // Act
    assertDoesNotThrow(() -> controller.signUp());

    // Assert
    verify(mockPreparedStatement).setString(1, "testUser");
    verify(mockPreparedStatement).setString(2, "password123");
    verify(mockPreparedStatement).setString(3, "test@example.com");
    verify(mockPreparedStatement).setString(4, "testUser");
    verify(mockPreparedStatement).setString(5, "test@example.com");
    verify(mockPreparedStatement).executeUpdate();
  }

  @Test
  void testSignUpWithExistingUser() throws Exception {
    // Arrange
    when(usernameTextField.getText()).thenReturn("existingUser");
    when(passwordTextField.getText()).thenReturn("password123");
    when(confirmPasswordTextField.getText()).thenReturn("password123");
    when(emailTextField.getText()).thenReturn("existing@example.com");
    when(mockPreparedStatement.executeUpdate()).thenReturn(0);

    // Act
    assertDoesNotThrow(() -> controller.signUp());

    // Assert
    verify(mockPreparedStatement).executeUpdate();
  }

  @Test
  void testSignUpWithDatabaseError() throws Exception {
    // Arrange
    when(usernameTextField.getText()).thenReturn("testUser");
    when(passwordTextField.getText()).thenReturn("password123");
    when(confirmPasswordTextField.getText()).thenReturn("password123");
    when(emailTextField.getText()).thenReturn("test@example.com");
    when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

    // Act & Assert
    assertDoesNotThrow(() -> controller.signUp());
  }

  @Test
  void testLoginView() {
    // Test không thể thực hiện đầy đủ vì phụ thuộc vào FXMLLoader và JavaFX runtime
    assertDoesNotThrow(() -> controller.loginView());
  }
}