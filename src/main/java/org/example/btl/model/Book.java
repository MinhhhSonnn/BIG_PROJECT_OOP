package org.example.btl.model;

import com.google.zxing.WriterException;
import java.io.IOException;
import org.example.btl.Util.QRCodeGenerator;

public class Book {

  private String bookName;
  private String author;
  private String ISBN;
  String description;
  private int quantity;
  String imageUrl;
  String publicationYear;
  private String category;

  public Book() {
    bookName = "";
    author = "";
    ISBN = "";
    description = "";
    quantity = 0;
    imageUrl = null;
    publicationYear = "";
    category = "";
  }

  public Book(String bookName, String author, String ISBN, String description, String imageUrl,
      String publicationYear, String category) {
    this.bookName = bookName;
    this.author = author;
    this.ISBN = ISBN;
    this.description = description;
    this.imageUrl = imageUrl;
    this.publicationYear = publicationYear;
    this.category = category;
  }

  public Book(String bookName, String author, String ISBN,
      int quantity, String description, String imageUrl, String publicationYear, String category) {
    this.bookName = bookName;
    this.author = author;
    this.ISBN = ISBN;
    this.quantity = quantity;
    this.description = description;
    this.imageUrl = imageUrl;
    this.publicationYear = publicationYear;
    this.category = category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;
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

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void generateQRCode(String outputPath) throws WriterException, IOException {
    // Tao chuoi thong tin cho qr code
    String qrData = String.format("Book Information:\n" +
            "ISBN: %s\n" +
            "Title: %s\n" +
            "Author: %s\n" +
            "Category: %s\n" +
            "Publication Year: %s",
        this.ISBN, this.bookName, this.author, this.category, this.publicationYear);

    // Tạo QR code
    QRCodeGenerator.generateQRCode(qrData, outputPath, 300, 300);
  }

}
