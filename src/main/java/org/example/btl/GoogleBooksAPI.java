package org.example.btl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.example.btl.model.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GoogleBooksAPI {

  private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

  // Tra ve doi tuong chua thong tin sach
  public static Book getBookInfo(String query) {
    String response = getApiResponse(query);
    if (response != null) {
      return parseBookInfo(response);
    }
    return null;
  }


  private static String getApiResponse(String query) {
    try {
      URL url = new URL(API_URL + query);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");// get ve
      connection.setRequestProperty("Accept", "application/json");

      try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        return response.toString(); // lay ra chuoi json
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // chuyen cai chuoi json thanh book
  private static Book parseBookInfo(String jsonResponse) {
    try {
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(jsonResponse);
      JSONArray items = (JSONArray) json.get("items");// chua danh sach cac cuon sach

      if (items != null && !items.isEmpty()) {
        JSONObject item = (JSONObject) items.get(0);
        JSONObject volumeInfo = (JSONObject) item.get("volumeInfo");//chua cac thong tin nhu tieu de, tg,...

        if (volumeInfo != null) {
          String title = (String) volumeInfo.get("title");
          String author = getAuthor(volumeInfo);
          String isbn = (String) item.get("id"); // ISBN có thể lấy từ id nếu không có trong volumeInfo
          String description = (String) volumeInfo.getOrDefault("description", "No description available");

          return new Book(title, author, isbn, description);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // Phương thức lấy tác giả, nếu không có trả về "Unknown"
  private static String getAuthor(JSONObject volumeInfo) {
    JSONArray authors = (JSONArray) volumeInfo.get("authors");
    if (authors != null && !authors.isEmpty()) {
      return authors.get(0).toString(); // Lấy tác giả đầu tiên
    }
    return "Unknown";
  }
}
