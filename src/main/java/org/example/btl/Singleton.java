package org.example.btl;

import javafx.stage.Stage;

public class Singleton {

  private static Stage stage;

  public static void setPrimaryStage(Stage stageStage) {
    if (stage == null) {
      stage = stageStage;

    }
  }

  public static Stage getPrimaryStage() {
    if (stage == null) {
      throw new IllegalStateException("PrimaryStage chưa được thiết lập");
    }
    return stage;
  }
}
