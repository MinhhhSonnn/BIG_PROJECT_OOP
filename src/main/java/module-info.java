module org.example.btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
  requires json.simple;

  opens org.example.btl.Controllers to javafx.fxml;
    exports org.example.btl;
  exports org.example.btl.api;
}