package org.example.btl.model;

public class Book {

  private int bookId;
  private String bookName;
  private String author;
  private String ISBN;
  private String status;
  String description;
  private int quantity;

  public Book() {
    bookId = 0;
    bookName = "";
    author = "";
    ISBN = "";
    status = "";
    description = "";
    quantity = 0;
  }
  public Book(String bookName, String author, String ISBN, String description) {
    this.bookName = bookName;
    this.author = author;
    this.ISBN = ISBN;
    this.description = description;
  }

  public Book(int bookId, String bookName, String author, String ISBN, String status,
      int quantity, String description) {
    this.bookId = bookId;
    this.bookName = bookName;
    this.author = author;
    this.ISBN = ISBN;
    this.status = status;
    this.quantity = quantity;
    this.description = description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  public String getDescription() {
    return description;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getISBN() {
    return ISBN;
  }

  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

}
