package ml.minli.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    public GridPane chessboard;

    public static Boolean isPrimary = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 15; i++) {
            RowConstraints rowConstraints = new RowConstraints(50, 50, 50);
            rowConstraints.setValignment(VPos.CENTER);
            chessboard.getRowConstraints().add(rowConstraints);
            ColumnConstraints columnConstraints = new ColumnConstraints(50, 50, 50);
            columnConstraints.setHalignment(HPos.CENTER);
            chessboard.getColumnConstraints().add(columnConstraints);
        }
        chessboard.setOnMouseClicked(mouseEvent -> {
            int x = (int) (mouseEvent.getX() / 50);
            int y = (int) (mouseEvent.getY() / 50);
            WritableImage oldWritableImage = new WritableImage(750, 750);
            chessboard.snapshot(null, oldWritableImage);
            if (oldWritableImage.getPixelReader().getArgb(x * 50 + 25, y * 50 + 25) != -1) {
                return;
            }
            Circle circle = new Circle();
            circle.setRadius(20);
            if (isPrimary == null || isPrimary) {
                circle.setFill(Paint.valueOf("#000001"));
                isPrimary = false;
            } else {
                circle.setFill(Paint.valueOf("#FFFFFE"));
                isPrimary = true;
            }
            chessboard.add(circle, x, y);
            WritableImage writableImage = new WritableImage(750, 750);
            chessboard.snapshot(null, writableImage);
            PixelReader pixelReader = writableImage.getPixelReader();
            int[][] color = new int[15][15];
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    int argb = pixelReader.getArgb(25 + 50 * i, 25 + 50 * j);
                    color[i][j] = argb;
                }
            }
            int[][] result = new int[15][15];
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    int argb = color[i][j];
                    if (argb == -1) {
                        result[i][j] = -1;
                    } else {
                        if (argb == -2) {
                            result[i][j] = 1;
                        } else {
                            result[i][j] = 0;
                        }
                    }
                }
            }
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    int currentResult = result[i][j];
                    if (currentResult == -1) {
                        continue;
                    }
                    //横格
                    if (i + 4 < 15) {
                        for (int m = 1; ; m++) {
                            if (!(result[i + m][j] == currentResult)) {
                                break;
                            } else {
                                if (m == 4) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    if (currentResult == 0) {
                                        alert.setContentText("黑赢!");
                                    } else {
                                        alert.setContentText("白赢!");
                                    }
                                    alert.show();
                                    List<Node> list = chessboard.getChildren().stream().filter(node -> node instanceof Circle).collect(Collectors.toList());
                                    chessboard.getChildren().removeAll(list);
                                    isPrimary = null;
                                    return;
                                }
                            }
                        }
                    }
                    //竖格
                    if (j + 4 < 15) {
                        for (int n = 1; ; n++) {
                            if (!(result[i][j + n] == currentResult)) {
                                break;
                            } else {
                                if (n == 4) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    if (currentResult == 0) {
                                        alert.setContentText("黑赢!");
                                    } else {
                                        alert.setContentText("白赢!");
                                    }
                                    alert.show();
                                    List<Node> list = chessboard.getChildren().stream().filter(node -> node instanceof Circle).collect(Collectors.toList());
                                    chessboard.getChildren().removeAll(list);
                                    isPrimary = null;
                                    return;
                                }
                            }
                        }
                    }
                    //斜格
                    if (i + 4 < 15 && j + 4 < 15) {
                        for (int t = 1; t <= 4; t++) {
                            if (!(result[i + t][j + t] == currentResult)) {
                                break;
                            } else {
                                if (t == 4) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    if (currentResult == 0) {
                                        alert.setContentText("黑赢!");
                                    } else {
                                        alert.setContentText("白赢!");
                                    }
                                    alert.show();
                                    List<Node> list = chessboard.getChildren().stream().filter(node -> node instanceof Circle).collect(Collectors.toList());
                                    chessboard.getChildren().removeAll(list);
                                    isPrimary = null;
                                }
                            }
                        }
                    }
                    //反斜格
                    if (i - 4 > -1 && j + 4 < 15) {
                        for (int t = 1; t <= 4; t++) {
                            if (!(result[i - t][j + t] == currentResult)) {
                                break;
                            } else {
                                if (t == 4) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    if (currentResult == 0) {
                                        alert.setContentText("黑赢!");
                                    } else {
                                        alert.setContentText("白赢!");
                                    }
                                    alert.show();
                                    List<Node> list = chessboard.getChildren().stream().filter(node -> node instanceof Circle).collect(Collectors.toList());
                                    chessboard.getChildren().removeAll(list);
                                    isPrimary = null;
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void play() {
        List<Node> list = chessboard.getChildren().stream().filter(node -> node instanceof Circle).collect(Collectors.toList());
        chessboard.getChildren().removeAll(list);
    }

    public void exit() {
        System.exit(0);
    }

}
