package org.example.btl.Controllers;

public class ValidationUtil {
  public static boolean isInteger(String str) {
    if(str == null || str.isEmpty()) return false;
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
