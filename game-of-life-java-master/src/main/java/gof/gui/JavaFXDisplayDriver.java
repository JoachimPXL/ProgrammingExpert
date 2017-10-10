package gof.gui;

import gof.core.Board;
import gof.core.Cell;
import gof.core.DisplayDriver;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JavaFXDisplayDriver implements DisplayDriver {
    private int size;
    private TilePane tilePane = new TilePane(5,5);

    public JavaFXDisplayDriver(int boardSize, int cellSizePx, Board board) {
        size = boardSize;
        tilePane.setPrefRows(boardSize);
        tilePane.setPrefColumns(boardSize);

        Cell[][] cells = board.getGrid();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Color color = cells[i][j].getState() ? Color.STEELBLUE : Color.WHITE;
                Rectangle rectangle = new Rectangle(cellSizePx, cellSizePx, color);
                tilePane.getChildren().add(rectangle);
                
                attachListeners(rectangle, cells[i][j]);
            }
        }
    }

    @Override
    public void displayBoard(Board board) {
        Cell[][] cells = board.getGrid();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Rectangle r = (Rectangle) tilePane.getChildren().get(boardToPaneCoords(i, j));
                r.setFill(cells[i][j].getState() ? Color.STEELBLUE : Color.WHITE);
            }
        }
    }

    public TilePane getPane() {
        return tilePane;
    }

    private int boardToPaneCoords(int i, int j) {
        return i * size + j;
    }
    
    private void attachListeners(Rectangle rectangle, Cell cell) {
        rectangle.setOnMousePressed(e -> { rectangle.setFill(Color.GRAY); });

        rectangle.setOnMouseClicked(e -> {
            rectangle.setFill(cell.getState() ? Color.WHITE : Color.STEELBLUE);
            cell.setNewState(!cell.getState());
            cell.updateState();
        });
    }
}
