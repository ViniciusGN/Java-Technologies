module com.example.imageviewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.imageviewer.image_viewer to javafx.fxml, javafx.graphics;
    exports com.example.imageviewer.image_viewer;
}