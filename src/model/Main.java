package model;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private static final Parent root = new Parent() {
    };
    private static final Scene mainScene = new Scene(root);
    private static Stage gameStage = new Stage(StageStyle.DECORATED);
    @FXML
    private Slider gridSizeSlider;

    static Scene getMainScene() {
        return mainScene;
    }

    @Override
    public void start(Stage mainStage) throws IOException {

        Parent titleRoot = FXMLLoader.load(getClass().getResource("../ui/Main.fxml"));
        mainScene.setRoot(titleRoot);
        gameStage = mainStage;
        gameStage.setResizable(true);
        gameStage.setTitle("Conway's Game Of Life");
        gameStage.setScene(mainScene);
        gameStage.show();


    }

    /**
     * Вызывается при нажатии кнопки воспроизведения. Передает значение из ползунка размера сетки в класс GameController
     * и изменяет размер главного окна в зависимости от размера кнопок ячеек и размеров сетки
     */
    @FXML
    protected void startGame() {

        GameController.initializeGame((int) gridSizeSlider.getValue());
        gameStage.setMinHeight(gridSizeSlider.getValue() * CellButton.getCellSize() * 1.2);
        gameStage.setMinWidth(gridSizeSlider.getValue() * CellButton.getCellSize());

    }
}