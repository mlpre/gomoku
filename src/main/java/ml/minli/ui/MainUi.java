package ml.minli.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ml.minli.util.ResourceUtil;

public class MainUi extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent main = FXMLLoader.load(ResourceUtil.getResource("fxml/main.fxml"));
        stage.setTitle("五子棋");
        Scene scene = new Scene(main);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setMinWidth(1120);
        stage.setMinHeight(920);
        stage.show();
    }
}
