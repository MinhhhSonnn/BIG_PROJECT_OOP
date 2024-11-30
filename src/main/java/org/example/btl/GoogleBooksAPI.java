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

  // tra ve doi tuong chua thong tin sach
  public static Book getBookInfo(String query) {
    try {
      String urlString = API_URL + query;
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
// luu toan bo phan hoi vao respone
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      // tra ve 1 chuoi json
      JSONParser parser = new JSONParser();
      JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
      if (jsonResponse.containsKey("items")) {
        JSONArray items = (JSONArray) jsonResponse.get("items");
        if (!items.isEmpty()) {
          JSONObject item = (JSONObject) items.get(0);
          if (item.containsKey("volumeInfo")) {
            JSONObject volumeInfo = (JSONObject) item.get("volumeInfo");

            // lay cac thong tin cua sach
            String title = (String) volumeInfo.get("title");
            String isbn = query;
            String author = ((JSONArray) volumeInfo.get("authors")).get(0).toString();
            return new Book(title, author, isbn);

          }
        }
      }
    } catch (Exception e) {

      e.getMessage();

    }
    return null;
  }

}
