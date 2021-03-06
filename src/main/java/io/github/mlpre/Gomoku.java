package io.github.mlpre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Gomoku extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent main = FXMLLoader.load(Gomoku.class.getClassLoader().getResource("fxml/main.fxml"));
        primaryStage.setTitle("五子棋");
        primaryStage.getIcons().add(new Image(Gomoku.class.getClassLoader().getResourceAsStream("img/icon.png")));
        Scene scene = new Scene(main);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
