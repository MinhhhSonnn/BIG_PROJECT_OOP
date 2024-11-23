module org.example.btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens org.example.btl to javafx.fxml;
    exports org.example.btl;
}