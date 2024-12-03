package org.example.btl.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.example.btl.model.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GoogleBooksAPI {

  private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

  // tạo ra mot thread pool voi 5 luong
  private static final ExecutorService executorService = Executors.newFixedThreadPool(5);


  public static List<Book> getBooksInfoConcurrently(List<String> queries) {
    List<Future<Book>> futures = new ArrayList<>();// dung de lu cac doi tuong bat dong bo
    List<Book> results = new ArrayList<>();

    // Submit cac task vao thread pool
    for (String query : queries) {
      Future<Book> future = executorService.submit(() -> getBookInfo(query));
      futures.add(future);
    }

    // lay ra ket qua
    for (Future<Book> future : futures) {
      try {
        Book book = future.get(20, TimeUnit.SECONDS);
        if (book != null) {
          results.add(book);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return results;

  }
// method bat dong bo de lay thong tin sach
  public static CompletableFuture<Book> getBookInfoAsync(String query) {
    return CompletableFuture.supplyAsync(() -> getBookInfo(query), executorService);
  }


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

      try (BufferedReader in = new BufferedReader(
          new InputStreamReader(connection.getInputStream()))) {
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
        JSONObject volumeInfo = (JSONObject) item.get(
            "volumeInfo");//chua cac thong tin nhu tieu de, tg,...

        if (volumeInfo != null) {
          String title = (String) volumeInfo.get("title");
          String author = getAuthor(volumeInfo);
          String isbn = (String) item.get("id");
          String description = (String) volumeInfo.getOrDefault("description",
              "No description available");
          String category = getCategory(volumeInfo);
          // lay anh bia sach
          String imageUrl = null;
          if (volumeInfo.containsKey("imageLinks")) {
            JSONObject imageLinks = (JSONObject) volumeInfo.get("imageLinks");
            if (imageLinks.containsKey("large")) {
              imageUrl = (String) imageLinks.get("large");
            } else if (imageLinks.containsKey("highRes")) {
              imageUrl = (String) imageLinks.get("highRes");
            } else if (imageLinks.containsKey("thumbnail")) {
              imageUrl = (String) imageLinks.get("thumbnail");
            }          }

          // lay nam xuat ban
          String publicationYear = (String) volumeInfo.get("publishedYear");
          Book book = new Book(title, author, isbn, description, imageUrl, publicationYear,
              category);
          return book;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // lay ten tac gia
  private static String getAuthor(JSONObject volumeInfo) {
    JSONArray authors = (JSONArray) volumeInfo.get("authors");
    if (authors != null && !authors.isEmpty()) {
      return authors.get(0).toString();
    }
    return "Unknown";
  }

  //lay the loai
  private static String getCategory(JSONObject volumeInfo) {
    JSONArray categories = (JSONArray) volumeInfo.get("categories");
    if (categories != null && !categories.isEmpty()) {
      return categories.get(0).toString();
    }
    return "No category available";
  }
// dong thread pool
  public static void shutdown() {
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
    }
  }
}
