package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Этот класс предоставляет отдельные "ячейки" для игровой сетки. Наследует класс кнопок JavaFX.
 */
class CellButton extends Button implements Comparable<CellButton> {

    private static final String COLOUR_OFF = "-fx-background-color: linear-gradient(grey,wheat)";
    private static final String COLOUR_ON = "-fx-background-color: linear-gradient(fuchsia, deepskyblue)";
    private static final double CELL_SIZE = 15;
    private static final Circle CELL_SHAPE = new Circle(CELL_SIZE / 2);
    private final int yPosition;
    private final int xPosition;
    private final ArrayList<Integer> neighbours;
    private boolean on;

    /**
     * @param x        X позиция в сетке
     * @param y        Y позиция в сетке
     * @param gridSize размер сетки
     */
    CellButton(int x, int y, int gridSize) {

        on = false;
        xPosition = x;
        yPosition = y;
        this.setShape(CELL_SHAPE);
        this.setStyle(COLOUR_OFF);
        this.setOnAction(value -> this.reverseState());
        setPrefSize(CELL_SIZE, CELL_SIZE);
        setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        neighbours = this.getNeighbours(gridSize);
        //TODO: get mouse drag working to turn cells on.
        EventHandler<MouseDragEvent> mouseDragHandler = event -> this.reverseState();
        this.setOnMouseDragOver(mouseDragHandler);
    }

    /**
     * Вычисляет координаты, которые охватывают края сетки. Используется для вычисления списка соседей ячейки.
     *
     * @param c        координата.
     * @param gridSize размер поля.
     * @return если c равно -1, возвращает размер сетки, а если c равно размеру сетки, возвращает 0.
     */
    private static int wrapCoordinate(int c, int gridSize) {
        int z;
        if (c == -1) {
            z = gridSize - 1;
        } else if (c == gridSize) {
            z = 0;
        } else z = c;
        return z;
    }

    static double getCellSize() {
        return CELL_SIZE;
    }

    /**
     * Создает список из восьми соседей ячейки во время построения, а не на каждой итерации сетки.
     * Это значительно повышает производительность метода runGame().
     *
     * @param gridSize размер поля.
     * @return список соседей ячейки.
     */
    private ArrayList<Integer> getNeighbours(int gridSize) {
        int x;
        int y;

        ArrayList<Integer> neighbours = new ArrayList<>();

        for (int i = this.yPosition - 1; i < this.yPosition + 2; i++) {
            for (int j = xPosition - 1; j < xPosition + 2; j++) {
                y = wrapCoordinate(i, gridSize);
                x = wrapCoordinate(j, gridSize);
                neighbours.add(x + (gridSize * y));
            }
        }
        neighbours.remove(4);
        return neighbours;
    }

    /**
     * Изменяет состояние ячейки на противоположное. Если ячейка выключена, для нее будет
     * установлено значение включено, и ее цвет изменится соответствующим образом, и наоборот.
     */
    void reverseState() {
        if (!this.on) {
            this.on = true;
            this.setStyle(COLOUR_ON);
        } else {
            this.on = false;
            this.setStyle(COLOUR_OFF);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellButton that = (CellButton) o;
        return yPosition == that.yPosition &&
                xPosition == that.xPosition;
    }

    @Override
    public int hashCode() {

        return Objects.hash(yPosition, xPosition);
    }

    @Override
    public String toString() {
        return "CellButton{" +
                "yPosition=" + yPosition +
                ", xPosition=" + xPosition +
                '}';
    }

    @Override
    public int compareTo(CellButton o) {
        if (this.yPosition > o.yPosition) {
            return 1;
        } else if (this.yPosition < o.yPosition)
            return -1;
        else return Integer.compare(this.xPosition, o.xPosition);
    }

    boolean isOn() {
        return on;
    }

    ArrayList<Integer> getNeighbours() {
        return neighbours;
    }

}