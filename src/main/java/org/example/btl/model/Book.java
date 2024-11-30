package org.example.btl.model;

public class Book {

  private String bookName;
  private String author;
  private String ISBN;
  private String status;
  String description;
  private int quantity;
  String imageUrl;
  String publicationYear;

  public Book() {
    bookName = "";
    author = "";
    ISBN = "";
    status = "";
    description = "";
    quantity = 0;
    imageUrl = null;
    publicationYear = "";
  }

  public Book(String bookName, String author, String ISBN, String description, String imageUrl,
      String publicationYear) {
    this.bookName = bookName;
    this.author = author;
    this.ISBN = ISBN;
    this.description = description;
    this.imageUrl = imageUrl;
    this.publicationYear = publicationYear;
  }

  public Book(int bookId, String bookName, String author, String ISBN, String status,
      int quantity, String description, String imageUrl, String publicationYear) {
    this.bookName = bookName;
    this.author = author;
    this.ISBN = ISBN;
    this.status = status;
    this.quantity = quantity;
    this.description = description;
    this.imageUrl = imageUrl;
    this.publicationYear = publicationYear;
  }

  public void setPublicationYear(String publicationYear) {
    this.publicationYear = publicationYear;
  }

  public String getPublicationYear() {
    return publicationYear;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
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
