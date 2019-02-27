package gameOfLife;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int height;
    private int width;
    private ArrayList<ArrayList<Cell>> board;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = createBoard();
    }

    private ArrayList<ArrayList<Cell>> createBoard() {
        ArrayList<ArrayList<Cell>> newBoard = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            ArrayList<Cell> row = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                row.add(new Cell());
            }
            newBoard.add(row);
        }
        return newBoard;
    }

    public void clearBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board.get(i).get(j).setAlive(false);
            }
        }
    }

    public void setRandomBoard() {
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board.get(i).get(j).setAlive(random.nextBoolean());
            }
        }
    }

    public void update() {
        ArrayList<ArrayList<Cell>> newBoard = createBoard();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighbours = getNumOfNeighbours(i, j);
                if (neighbours == 3 || (board.get(i).get(j).isAlive() && neighbours == 2)) {
                    newBoard.get(i).get(j).setAlive(true);
                }
            }
        }
        board = newBoard;
    }

    private int getNumOfNeighbours(int row, int column) {
        int count = 0;
        for (int i = Math.max(row - 1, 0); i <= Math.min(row + 1, height - 1); i++) {
            for (int j = Math.max(column - 1, 0); j <= Math.min(column + 1, width - 1); j++) {
                if ((i != row || j != column) && board.get(i).get(j).isAlive()) {
                    ++count;
                }
            }
        }
        return count;
    }

    public Boolean isCellAlive(int row, int column) {
        return board.get(row).get(column).isAlive();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-");
        for (int j = 0; j <= width; j++) {
            stringBuilder.append("--");
        }
        stringBuilder.append("\n");
        for (int i = 0; i < height; i++) {
            stringBuilder.append("| ");
            for (int j = 0; j < width; j++) {
                stringBuilder.append(board.get(i).get(j)).append(" ");
            }
            stringBuilder.append("|").append("\n");
        }
        stringBuilder.append("-");
        for (int j = 0; j <= width; j++) {
            stringBuilder.append("--");
        }
        return stringBuilder.toString();
    }
}
