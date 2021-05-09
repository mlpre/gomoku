module gomoku {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    opens fxml;
    opens img;
    opens ml.minli to javafx.graphics, javafx.controls, javafx.fxml;
    opens ml.minli.controller to javafx.graphics, javafx.controls, javafx.fxml;
}