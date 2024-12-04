module org.example.btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
  requires json.simple;
  requires java.mail;

  opens org.example.btl.Controllers to javafx.fxml;
    exports org.example.btl;
  exports org.example.btl.api;
  opens org.example.btl.Database to javafx.fxml;
  opens org.example.btl.model to javafx.base, javafx.fxml;
  opens org.example.btl.service to javafx.base, javafx.fxml;
}