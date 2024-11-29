package org.example.btl.model;

public abstract class User {
  private String userName;
  private String password;
  private String email;


  public User(){
    userName = "";
    password = "";
    email = "";

  }

  public User(String userName, String password, String name, String email, int age){
    this.userName = userName;
    this.password = password;
    this.email = email;

  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public abstract String getRole();

}