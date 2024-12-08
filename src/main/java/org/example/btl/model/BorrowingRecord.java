package org.example.btl.model;

import java.time.LocalDate;

public class BorrowingRecord {

  private String ISBN;
  private String bookName;
  private String userName;
  private LocalDate borrowDate;

  private LocalDate returnDate; // ngay thuc te tra sach

  public BorrowingRecord() {
    ISBN = null;
    bookName = null;
    userName = null;
    borrowDate = null;
    returnDate = null;
  }

  public BorrowingRecord(String ISBN, String bookName, String userName, LocalDate borrowDate,
      LocalDate returnDate) {
    this.ISBN = ISBN;
    this.bookName = bookName;
    this.userName = userName;
    this.borrowDate = borrowDate;

    this.returnDate = returnDate;
  }

  public String getISBN() {
    return ISBN;
  }

  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public LocalDate getBorrowDate() {
    return borrowDate;
  }

  public void setBorrowDate(LocalDate borrowDate) {
    this.borrowDate = borrowDate;
  }


  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

}
