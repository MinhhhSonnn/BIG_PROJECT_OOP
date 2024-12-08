package org.example.btl;

public class informationUserName {
  public static String userName;
  public static int numberBorrowedBooks;
  public static int numberViolations;

  public informationUserName() {
  }

  public static String getUserName() {
    return userName;
  }

  public static void setUserName(String userName) {
    informationUserName.userName = userName;
  }

  public static int getNumberBorrowedBooks() {
    return numberBorrowedBooks;
  }

  public static void setNumberBorrowedBooks(int numberBorrowedBooks) {
    informationUserName.numberBorrowedBooks = numberBorrowedBooks;
  }

  public static int getNumberViolations() {
    return numberViolations;
  }

  public static void setNumberViolations(int numberViolations) {
    informationUserName.numberViolations = numberViolations;
  }
}
