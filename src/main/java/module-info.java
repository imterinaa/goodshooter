module com.example.goodshooter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.goodshooter to javafx.fxml;
    exports com.example.goodshooter;
}