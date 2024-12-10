package org.example.btl.model;

public class Manager {

  private int ID;
  private String employeeName;
  private String sex;
  private String position;
  private String phone;
  private String email;

  public Manager(int ID, String employeeName, String sex, String position, String phone,
      String email) {
    this.ID = ID;
    this.employeeName = employeeName;
    this.sex = sex;
    this.position = position;
    this.phone = phone;
    this.email = email;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
