package org.example.btl.model;

public abstract class User {
  private String userName;
  private String password;
  private String name;
  private String email;
  private int age;

  public User(){
    userName = "";
    password = "";
    name = "";
    email = "";
    age = 0;
  }

  public User(String userName, String password, String name, String email, int age){
    this.userName = userName;
    this.password = password;
    this.name = name;
    this.email = email;
    this.age = age;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
  public abstract String getRole();

}