package org.example.btl;

public class InformationUserName {

  public static String userName;
  public static int numberBorrowedBooks;
  public static int numberViolations;

  public InformationUserName() {
  }

  public static String getUserName() {
    return userName;
  }

  public static void setUserName(String userName) {
    InformationUserName.userName = userName;
  }

  public static int getNumberBorrowedBooks() {
    return numberBorrowedBooks;
  }

  public static void setNumberBorrowedBooks(int numberBorrowedBooks) {
    InformationUserName.numberBorrowedBooks = numberBorrowedBooks;
  }

  public static int getNumberViolations() {
    return numberViolations;
  }

  public static void setNumberViolations(int numberViolations) {
    InformationUserName.numberViolations = numberViolations;
  }
}
