package org.example.btl.model;

public class Reader extends User {

  private int numberOfBooksBorrow;
  private int numberOfViolations;

  public Reader() {
    super();
    numberOfBooksBorrow = 0;
    numberOfViolations = 0;
  }

  public Reader(String userName, String password, String email, int numberOfBooksBorrow,
      int numberOfViolations) {
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

  public void setNumberOfBooksBorrow(int numberOfBooksBorrow) throws IllegalArgumentException {
    if (numberOfBooksBorrow < 0) {
      throw new IllegalArgumentException();
    }
    this.numberOfBooksBorrow = numberOfBooksBorrow;
  }

  public int getNumberOfViolations() {
    return numberOfViolations;
  }

  public void setNumberOfViolations(int numberOfViolations) throws IllegalArgumentException {
    if (numberOfViolations < 0) {
      throw new IllegalArgumentException();
    }
    this.numberOfViolations = numberOfViolations;
  }
}
