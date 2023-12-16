package model;

import javafx.fxml.FXML;

/**
 * Переходит к основному классу после нажатия кнопки воспроизведения на титульном экране
 */

class GameController {

    /**
     * Вызывает конструктор   игровой сетки, передавая значение размера из ползунка размера сетки на главном экране,
     * а затем назначает игровую сетку в качестве нового root главной сцены.
     *
     * @param size размер поля.
     */
    @FXML
    static void initializeGame(int size) {

        GameGrid gameGrid = new GameGrid(size);
        Main.getMainScene().setRoot(gameGrid);

    }

}