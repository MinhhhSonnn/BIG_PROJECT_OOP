module org.example.btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens org.example.btl.Controllers to javafx.fxml;
    exports org.example.btl;
}