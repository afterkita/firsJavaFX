package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Сетка из "ячеек", составляющих игровую область. Наследует класс JavaFX GridPane.
 */

class GameGrid extends GridPane {

    private final Button startStopButton; //методы изменяют его с stop на start и наоборот
    private Timeline gameTimeline;
    private ArrayList<CellButton> cellList;

    /**
     * Конструктор для класса Game Grid. Принимает целочисленное значение размера и создает
     * квадратную сетку кнопок ячеек, а также панель кнопок с кнопкой остановки/ запуска,
     * кнопкой очистки и кнопкой выхода.
     * @param gridSize размер квадратной сетки
     */

    GameGrid(int gridSize) {

        populateCells(gridSize);
        setAlignment(Pos.CENTER);

        startStopButton = new Button("Start");
        startStopButton.setDefaultButton(true);
        startStopButton.setOnAction(value -> runGame());
        startStopButton.setPadding(new Insets(10));


        Button clearButton = new Button("Clear");
        clearButton.setOnAction(value -> clearAllCells());
        clearButton.setPadding(new Insets(10));

        Button exitButton = new Button("Exit");
        exitButton.setCancelButton(true);
        exitButton.setOnAction(e -> Platform.exit());
        exitButton.setPadding(new Insets(10));

        ButtonBar controlButtons = new ButtonBar();

        controlButtons.getButtons().add(startStopButton);
        controlButtons.getButtons().add(clearButton);
        controlButtons.getButtons().add(exitButton);
        controlButtons.setPadding(new Insets(10));

        this.add(controlButtons, 0, gridSize + 1, gridSize, 1);
        setPrefSize(CellButton.getCellSize() * gridSize * 1.2, CellButton.getCellSize() * gridSize * 1.2);
        setMinSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);


    }

    /**
     * Заполняет игровую сетку необходимым количеством кнопок ячеек (gridSize * Размер сетки).
     * Также создает список ячеек для упрощения доступа. Ячейки добавляются в том порядке,
     * в котором они были созданы, и следовательно, * могут быть доступны по индексу их списка
     * (например, ячейка с x:5, y:8 будет иметь индекс 85).
     *
     * @param gridSize размер квадратной сетки
     */

    private void populateCells(int gridSize) {

        cellList = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                CellButton b = new CellButton(j, i, gridSize);
                this.add(b, j, i);
                cellList.add(b);
            }
        }
    }

    /**
     * Выполняется бесконечно во время игры.
     * Метод будет продолжать запускать каждый ключевой кадр до тех пор, пока не будет нажата кнопка остановки или выхода.
     */

    private void runGame() {

        gameTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), (actionEvent -> evolveGrid())));
        startStopButton.setText("Stop");
        startStopButton.setOnAction(value -> pauseGame());
        gameTimeline.setCycleCount(Timeline.INDEFINITE);
        gameTimeline.play();

    }

    /**
     * Основная логика игры жизни.
     * Повторяет сетку в соответствии с правилами: любая живая ячейка с менее чем двумя или более чем тремя
     * живыми соседями умирает; любая мертвая ячейка с ровно тремя живыми соседями оживает.
     * Этот метод вызывается методом runGame() для каждого кадра.
     */
    private void evolveGrid() {


        List<CellButton> switchButtons = new ArrayList<>();

        for (CellButton cell : cellList) {
            int liveNeighbours = getLiveNeighbours(cell.getNeighbours());
            if (cell.isOn()) {
                if (liveNeighbours < 2 || liveNeighbours > 3) {
                    switchButtons.add(cell);
                }
            } else if (liveNeighbours == 3) {
                switchButtons.add(cell);
            }
        }

        for (CellButton c : switchButtons) {
            c.reverseState();
        }
    }

    /**
     * Подсчитывает живых соседей для каждой ячейки в сетке.
     *
     * @param list Список соседей ячейки (который является полем CellButton).
     * @return Количество живых соседей для данной ячейки.
     */
    private int getLiveNeighbours(ArrayList<Integer> list) {

        int count = 0;
        for (Integer i : list)
            if (cellList.get(i).isOn())
                count++;
        return count;

    }

    /**
     * Приостанавливает игру при нажатии кнопки "Стоп". Заменяет кнопку "Стоп" на кнопку "Пуск".
     */
    private void pauseGame() {

        gameTimeline.stop();
        startStopButton.setText("Start");
        startStopButton.setOnAction(value -> runGame());

    }

    /**
     * Устанавливает для всех ячеек значение выкл.
     */
    private void clearAllCells() {

        for (CellButton c : cellList) {
            if (c.isOn()) c.reverseState();
        }
    }

}