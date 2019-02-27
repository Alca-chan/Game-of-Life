package gameOfLife.swingApp;

import gameOfLife.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JPanel implements ActionListener {
    private static final int MAX_CELL_SIZE = 60;
    private static final int SPACE = 2;
    private static final int DELAY = 500;

    private int boardHeight;
    private int boardWidth;
    private int numOfGenerations;
    private int currentGeneration;
    private Board board;

    private Timer timer = new Timer(DELAY, this);

    private JFormattedTextField heightField;
    private JFormattedTextField widthField;
    private JFormattedTextField generationsField;
    private JButton OKButton;
    private JPanel mainPanel;
    private JPanel boardPanel;

    public Frame() {
        OKButton.addActionListener(e -> initBoard());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initBoard() {
        timer.stop();
        if (!(notEmpty(heightField, "Height") &&
                notEmpty(widthField, "Width") &&
                notEmpty(generationsField, "Generations"))) {
            return;
        }
        boardHeight = Integer.parseInt(this.heightField.getText());
        boardWidth = Integer.parseInt(this.widthField.getText());
        numOfGenerations = Integer.parseInt(this.generationsField.getText());
        currentGeneration = 0;

        board = new Board(boardHeight, boardWidth);
        board.setRandomBoard();
        System.out.println("board " + boardHeight + "x" + boardWidth + " created");

        timer.start();
    }

    private boolean notEmpty(JTextField textField, String fieldKey) {
        if (textField.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    fieldKey + " cannot be empty",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void draw(Graphics g)
    {
        int preferredCellHeight = boardPanel.getHeight() / boardHeight;
        int preferredCellWidth = boardPanel.getWidth() / boardWidth;
        int cellSize = Math.min(preferredCellHeight, preferredCellWidth) - SPACE;
        cellSize = Math.min(cellSize, MAX_CELL_SIZE);
        int offsetTop = (boardPanel.getHeight() - ((cellSize + SPACE) * boardHeight)) / 2;
        int offsetLeft = (boardPanel.getWidth() - ((cellSize + SPACE) * boardWidth)) / 2;

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (board.isCellAlive(i, j)) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * (cellSize + SPACE) + offsetLeft, i * (cellSize + SPACE) + offsetTop, cellSize, cellSize);
            }
        }
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++currentGeneration;
        board.update();
        draw(boardPanel.getGraphics());
        System.out.println(currentGeneration + ". generation");
        if (currentGeneration == numOfGenerations) { //numOfGenerations <= 0 is infinity loop
            timer.stop();
        }
    }
}
