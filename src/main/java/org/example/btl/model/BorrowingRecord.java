package org.example.btl.model;

import java.time.LocalDate;

public class BorrowingRecord {

  private String isbn;
  private String bookName;
  private Reader reader;
  private LocalDate borrowDate;

  private LocalDate returnDate; // ngay thuc te tra sach

  public BorrowingRecord() {
    isbn = null;
    bookName = null;
    reader = null;
    borrowDate = null;
    returnDate = null;
  }

  public BorrowingRecord(String isbn, String bookName, Reader reader, LocalDate borrowDate,
      LocalDate dueDate,
      LocalDate returnDate) {
    this.isbn = isbn;
    this.bookName = bookName;
    this.reader = reader;
    this.borrowDate = borrowDate;

    this.returnDate = returnDate;
  }
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public Reader getReader() {
    return reader;
  }

  public void setReader(Reader reader) {
    this.reader = reader;
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
