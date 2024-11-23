package org.example.btl;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Scene;

public class Main extends Application{
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 780, 460);
    stage.setTitle("UET Library Management");
    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch();
  }
}
