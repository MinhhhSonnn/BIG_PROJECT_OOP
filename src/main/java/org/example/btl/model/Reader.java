package org.example.btl.model;

public class Reader extends User{
  private int numberOfBooksBorrow;
  private int numberOfViolations;
  public Reader() {
    super();
    numberOfBooksBorrow = 0;
    numberOfViolations = 0;
  }
  public Reader(String userName, String password, String email, int numberOfBooksBorrow, int numberOfViolations) {
    super(userName, password, email);
    this.numberOfBooksBorrow = numberOfBooksBorrow;
    this.numberOfViolations = numberOfViolations;
  }
  @Override
  public String getRole() {
    return "Reader";
  }
  public int getNumberOfBooksBorrow() {
    return numberOfBooksBorrow;
  }
  public void setNumberOfBooksBorrow(int numberOfBooksBorrow) {
    this.numberOfBooksBorrow = numberOfBooksBorrow;
  }
}
