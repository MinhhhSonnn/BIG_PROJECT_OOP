package org.example.btl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;

public class Main extends Application {

  private double xOffset = 0;
  private double yOffset = 0;

  // Biến để giữ kết quả tải login.fxml
  private Parent loginRoot = null;

  @Override
  public void start(Stage stage) {
    try {
      // 1.thiet lap stage
      stage.initStyle(StageStyle.DECORATED);

      // 2.tao layout cho vid
      StackPane introLayout = new StackPane();

      // 3.tai vid
      String videoPath = "src/main/resources/org/example/images/intro.mp4";
      Media media = new Media(new File(videoPath).toURI().toString());
      MediaPlayer mediaPlayer = new MediaPlayer(media);
      MediaView mediaView = new MediaView(mediaPlayer);

      // kich thuoc vid
      mediaView.setFitWidth(780);
      mediaView.setFitHeight(460);

      introLayout.getChildren().add(mediaView);

      Scene introScene = new Scene(introLayout);
      stage.setScene(introScene);

      // 4. phat vid
      mediaPlayer.play();

      // tai login tren luong nen
      Task<Parent> loadLoginTask = new Task<Parent>() {
        @Override
        protected Parent call() throws Exception {
          return FXMLLoader.load(getClass().getResource("login.fxml"));
        }
      };

      // gan vao loginRoot
      loadLoginTask.setOnSucceeded(event -> {
        loginRoot = loadLoginTask.getValue();
      });

      // thong bao khi load khong thanh cong
      loadLoginTask.setOnFailed(event -> {
        loadLoginTask.getException().printStackTrace();

      });

      // khoi dong task tren luong moi
      Thread loadLoginThread = new Thread(loadLoginTask);
      loadLoginThread.setDaemon(true); // dam bao khong thoat
      loadLoginThread.start();

      // xu lí sự kiện khi vid kết thúc
      mediaPlayer.setOnEndOfMedia(() -> {
        Platform.runLater(() -> {
          if (loginRoot != null) {
            // chuyen doi khi tai xong
            switchToLoginScene(stage, loginRoot, mediaPlayer);
          } else {
            // chưa xong thi doi
            waitForLoginLoad(stage, mediaPlayer);
          }
        });
      });

      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // chuyen doi sang man hinh dang nhap
  private void switchToLoginScene(Stage stage, Parent loginRoot, MediaPlayer mediaPlayer) {
    Scene loginScene = new Scene(loginRoot);

    // thiet lap su kien keo tha
    loginRoot.setOnMousePressed((MouseEvent event) -> {
      xOffset = event.getSceneX();
      yOffset = event.getSceneY();
    });

    loginRoot.setOnMouseDragged((MouseEvent event) -> {
      stage.setX(event.getScreenX() - xOffset);
      stage.setY(event.getScreenY() - yOffset);
    });

    // cap nhat scene cho stage
    stage.setScene(loginScene);
    stage.setResizable(false);
    stage.setTitle("UET Library Management");

    // giai phong video
    mediaPlayer.stop();
    mediaPlayer.dispose();
  }


  private void waitForLoginLoad(Stage stage, MediaPlayer mediaPlayer) {
    // Tạo một Task để kiểm tra xem login.fxml đã được tải chưa
    Task<Void> waitTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        // Đợi cho đến khi loginRoot không còn null
        while (loginRoot == null) {
          Thread.sleep(100); // Kiểm tra mỗi 100ms
        }
        return null;
      }
    };

    waitTask.setOnSucceeded(event -> {
      Platform.runLater(() -> {
        switchToLoginScene(stage, loginRoot, mediaPlayer);
      });
    });

    waitTask.setOnFailed(event -> {
      waitTask.getException().printStackTrace();
    });

    // Khởi động Task trên một luồng mới
    Thread waitThread = new Thread(waitTask);
    waitThread.setDaemon(true);
    waitThread.start();
  }

  public static void main(String[] args) {
    launch();
  }
}