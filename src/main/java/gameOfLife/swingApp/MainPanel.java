package gameOfLife.swingApp;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import gameOfLife.Board;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel implements ActionListener {
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
    private JPanel panel;
    private JPanel boardPanel;
    private JPanel optionsPanel;

    public MainPanel() {
        OKButton.addActionListener(e -> initBoard());

        KeyAdapter enter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    initBoard();
                }
            }
        };
        heightField.addKeyListener(enter);
        widthField.addKeyListener(enter);
        generationsField.addKeyListener(enter);
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initBoard() {
        timer.stop();
        panel.repaint();

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

    public void draw(Graphics g) {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayoutManager(7, 2, new Insets(20, 20, 20, 20), -1, -1));
        panel.add(optionsPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(250, -1), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Height");
        optionsPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heightField = new JFormattedTextField();
        optionsPanel.add(heightField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Width");
        optionsPanel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        widthField = new JFormattedTextField();
        optionsPanel.add(widthField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Generations");
        optionsPanel.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generationsField = new JFormattedTextField();
        optionsPanel.add(generationsField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        OKButton = new JButton();
        OKButton.setText("OK");
        optionsPanel.add(OKButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        optionsPanel.add(spacer1, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        optionsPanel.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        optionsPanel.add(spacer3, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(boardPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(194, 24), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}