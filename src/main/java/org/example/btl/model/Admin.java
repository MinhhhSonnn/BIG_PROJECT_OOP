package org.example.btl.model;

import org.example.btl.service.BookService;

public class Admin extends User {
  private BookService bookService;
  public Admin() {
    super();
  }
  public Admin(String userName, String password, String name, String email, int age, BookService bookService){
    super(userName, password, name, email, age);
    this.bookService = bookService;
  }
  @Override
  public String getRole() {
    return "Admin";
  }
  public boolean addBook(Book book){
    return false;
  }
  public boolean removeBook(Book book){
    return false;
  }
  public boolean updateBook(Book book){
    return false;
  }


}
