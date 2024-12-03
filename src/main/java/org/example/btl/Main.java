package org.example.btl;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import org.example.btl.api.GoogleBooksAPI;
import org.example.btl.model.Book;

public class Main extends Application {

  private double x = 0;
  private double y = 0;

  @Override
  public void start(Stage stage) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

    Scene scene = new Scene(root);

    root.setOnMousePressed((MouseEvent event) -> {
      x = event.getSceneX();
      y = event.getSceneY();
    });

    root.setOnMouseDragged((MouseEvent event) -> {
      stage.setX(event.getScreenX() - x);
      stage.setY(event.getScreenY() - y);
    });

    //stage.initStyle(StageStyle.TRANSPARENT); // tat thanh dieu huong
    stage.setResizable(false); // tat nut maximine
    stage.setTitle("UET Library Management");
    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch();
  }
}
