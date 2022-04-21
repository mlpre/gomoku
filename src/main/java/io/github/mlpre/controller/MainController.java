package io.github.mlpre.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    public StackPane root;
    @FXML
    public GridPane chessboard;

    private double xOffset = 0;

    private double yOffset = 0;

    public static Boolean isPrimary = null;

    //棋盘格子数量
    public static int chessboardNumber = 20;

    //棋盘格子大小
    public static int chessboardSize = 40;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler<MouseEvent> eventHandler = mouseEvent -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        };
        root.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getY() < 20) {
                root.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandler);
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        root.setOnMouseReleased(mouseEvent -> root.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandler));
        for (int i = 0; i < chessboardNumber; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            chessboard.getRowConstraints().add(rowConstraints);
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHalignment(HPos.CENTER);
            chessboard.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < chessboardNumber; i++) {
            for (int j = 0; j < chessboardNumber; j++) {
                StackPane stackPane = new StackPane();
                stackPane.setMinSize(chessboardSize, chessboardSize);
                stackPane.setMaxSize(chessboardSize, chessboardSize);
                stackPane.setPrefSize(chessboardSize, chessboardSize);
                String border = "";
                if (i == 0 && j == 0) {
                    border = "-fx-border-width: 2 1 1 2;";
                } else if (i == chessboardNumber - 1 && j == chessboardNumber - 1) {
                    border = "-fx-border-width: 1 2 2 1;";
                } else if (i == 0 && j == chessboardNumber - 1) {
                    border = "-fx-border-width: 1 1 2 2;";
                } else if (i == chessboardNumber - 1 && j == 0) {
                    border = "-fx-border-width: 2 2 1 1;";
                } else if (i == 0 && j < chessboardNumber - 1) {
                    border = "-fx-border-width: 1 1 1 2;";
                } else if (j == 0 && i < chessboardNumber - 1) {
                    border = "-fx-border-width: 2 1 1 1;";
                } else if (i == chessboardNumber - 1 && j < chessboardNumber - 1) {
                    border = "-fx-border-width: 1 2 1 1;";
                } else if (j == chessboardNumber - 1 && i < chessboardNumber - 1) {
                    border = "-fx-border-width: 1 1 2 1;";
                }
                stackPane.setStyle("-fx-border-color: #987347;" + border);
                chessboard.add(stackPane, i, j);
            }
        }
    }

    public void play() {
        ObservableList<Node> children = chessboard.getChildren();
        children.forEach(currentNode -> {
            if (currentNode instanceof StackPane) {
                ((StackPane) currentNode).getChildren().clear();
            }
        });
        isPrimary = null;
    }

    public void exit() {
        System.exit(0);
    }

    public synchronized void addChessPiece(MouseEvent mouseEvent) {
        Node node = mouseEvent.getPickResult().getIntersectedNode();
        if (!(node instanceof StackPane)) {
            return;
        }
        if (!((StackPane) node).getChildren().isEmpty()) {
            return;
        }
        Circle circle = new Circle();
        circle.setRadius(chessboardSize * 0.4);
        if (isPrimary == null || isPrimary) {
            circle.setFill(Paint.valueOf("#000001"));
            isPrimary = false;
        } else {
            circle.setFill(Paint.valueOf("#FFFFFE"));
            isPrimary = true;
        }
        ((StackPane) node).getChildren().add(circle);
        getResult();
    }

    public void getResult() {
        int[][] result = getChessboardResult();
        for (int i = 0; i < chessboardNumber; i++) {
            for (int j = 0; j < chessboardNumber; j++) {
                int currentResult = result[i][j];
                if (currentResult == -1) {
                    continue;
                }
                //横格
                if (i + 4 < chessboardNumber) {
                    for (int m = 1; ; m++) {
                        if (!(result[i + m][j] == currentResult)) {
                            break;
                        } else {
                            if (m == 4) {
                                showResult(currentResult);
                                return;
                            }
                        }
                    }
                }
                //竖格
                if (j + 4 < chessboardNumber) {
                    for (int n = 1; ; n++) {
                        if (!(result[i][j + n] == currentResult)) {
                            break;
                        } else {
                            if (n == 4) {
                                showResult(currentResult);
                                return;
                            }
                        }
                    }
                }
                //斜格
                if (i + 4 < chessboardNumber && j + 4 < chessboardNumber) {
                    for (int t = 1; ; t++) {
                        if (!(result[i + t][j + t] == currentResult)) {
                            break;
                        } else {
                            if (t == 4) {
                                showResult(currentResult);
                                return;
                            }
                        }
                    }
                }
                //反斜格
                if (i - 4 > -1 && j + 4 < chessboardNumber) {
                    for (int t = 1; ; t++) {
                        if (!(result[i - t][j + t] == currentResult)) {
                            break;
                        } else {
                            if (t == 4) {
                                showResult(currentResult);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private int[][] getChessboardResult() {
        //所有棋子位置
        Map<String, List<Node>> collect = chessboard.getChildren().stream().collect(
                Collectors.groupingBy(currentNode -> GridPane.getRowIndex(currentNode) + "-" + GridPane.getColumnIndex(currentNode)));
        int[][] result = new int[chessboardNumber][chessboardNumber];
        for (int i = 0; i < chessboardNumber; i++) {
            for (int j = 0; j < chessboardNumber; j++) {
                StackPane stackPane = (StackPane) collect.get(i + "-" + j).get(0);
                if (stackPane.getChildren().isEmpty()) {
                    result[i][j] = -1;
                } else {
                    Node currentNode = stackPane.getChildren().get(0);
                    Circle currentCircle = (Circle) currentNode;
                    if (currentCircle.getFill().equals(Paint.valueOf("#FFFFFE"))) {
                        result[i][j] = 1;
                    } else {
                        result[i][j] = 0;
                    }
                }
            }
        }
        return result;
    }

    public void showResult(int currentResult) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (currentResult == 0) {
            alert.setContentText("黑子赢!");
        } else {
            alert.setContentText("白子赢!");
        }
        alert.show();
        alert.setOnCloseRequest(dialogEvent -> {
            ObservableList<Node> children = chessboard.getChildren();
            children.forEach(currentNode -> {
                if (currentNode instanceof StackPane) {
                    ((StackPane) currentNode).getChildren().clear();
                }
            });
        });
        isPrimary = null;
    }

}
