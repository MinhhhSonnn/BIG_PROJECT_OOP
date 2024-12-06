package org.example.btl.model;

public class Admin extends User {
  public Admin() {
    super();
  }
  public Admin( String userName, String password, String email){
    super( userName, password, email);
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
