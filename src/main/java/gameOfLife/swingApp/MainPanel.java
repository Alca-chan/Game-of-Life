package gameOfLife.swingApp;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import gameOfLife.Board;

import javax.swing.*;
import java.awt.*;
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
    private int speed;
    private Board board;

    private Timer timer = new Timer(DELAY, this);

    private JFormattedTextField heightField;
    private JFormattedTextField widthField;
    private JFormattedTextField generationsField;
    private JButton OKButton;
    private JPanel panel;
    private JPanel boardPanel;
    private JPanel optionsPanel;
    private JSlider speedSlider;
    private JLabel speedLabel;
    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel generationsLabel;
    private JButton stopButton;
    private JTextArea output;

    public MainPanel() {
        OKButton.addActionListener(e -> go());

        KeyAdapter enter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    go();
                }
            }
        };
        heightField.addKeyListener(enter);
        widthField.addKeyListener(enter);
        generationsField.addKeyListener(enter);

        stopButton.addActionListener(e -> stop());
    }

    public JPanel getPanel() {
        return panel;
    }

    private void stop() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    private void go() {
        if (!(notEmpty(heightField, "Height") &&
                notEmpty(widthField, "Width") &&
                notEmpty(generationsField, "Generations"))) {
            return;
        }
        int newBoardHeight = Integer.parseInt(this.heightField.getText());
        int newBoardWidth = Integer.parseInt(this.widthField.getText());
        int newNumOfGenerations = Integer.parseInt(this.generationsField.getText());
        int newSpeed = this.speedSlider.getValue();

        if (!timer.isRunning() || newBoardHeight != boardHeight || newBoardWidth != newBoardHeight) {
            initBoard(newBoardHeight, newBoardWidth, newNumOfGenerations, newSpeed);
        } else {
            if (newNumOfGenerations != numOfGenerations) {
                editGenerations(newNumOfGenerations);
            }
            if (newSpeed != speed) {
                editSpeed(newSpeed);
            }
        }
    }

    private void initBoard(int newBoardHeight, int newBoardWidth, int newNumOfGenerations, int newSpeed) {
        timer.stop();
        panel.repaint();

        boardHeight = newBoardHeight;
        boardWidth = newBoardWidth;
        numOfGenerations = newNumOfGenerations;
        currentGeneration = 0;
        speed = newSpeed;

        timer = new Timer((int) Math.round(DELAY / (speed / 100.0)), this);

        board = new Board(boardHeight, boardWidth);
        board.setRandomBoard();
        output.append("New board created: " + boardHeight + " x " + boardWidth + "\n");

        timer.start();
    }

    private void editGenerations(int newNumOfGenerations) {
        timer.stop();
        if (newNumOfGenerations < currentGeneration && newNumOfGenerations > 0) {
            JOptionPane.showMessageDialog(null,
                    "Generations cannot be less than current generation (" + currentGeneration + ")",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            output.append("Generations changed: " + numOfGenerations + " -> " + newNumOfGenerations + "\n");
            numOfGenerations = newNumOfGenerations;
        }
        timer.start();
    }

    private void editSpeed(int newSpeed) {
        timer.stop();
        output.append("Speed changed: x" + speed / 100.0 + " -> x" + newSpeed / 100.0 + "\n");
        speed = newSpeed;
        timer = new Timer((int) Math.round(DELAY / (speed / 100.0)), this);
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
        output.append(currentGeneration + ". generation\n");
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
        optionsPanel.setLayout(new GridLayoutManager(9, 5, new Insets(20, 20, 20, 20), -1, -1));
        panel.add(optionsPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        heightLabel = new JLabel();
        heightLabel.setText("Height");
        optionsPanel.add(heightLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heightField = new JFormattedTextField();
        optionsPanel.add(heightField, new GridConstraints(1, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(200, 24), null, 0, false));
        widthLabel = new JLabel();
        widthLabel.setText("Width");
        optionsPanel.add(widthLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        widthField = new JFormattedTextField();
        optionsPanel.add(widthField, new GridConstraints(2, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(200, 24), null, 0, false));
        generationsLabel = new JLabel();
        generationsLabel.setText("Generations");
        optionsPanel.add(generationsLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generationsField = new JFormattedTextField();
        generationsField.setToolTipText("Any integer, 0 for infinity");
        optionsPanel.add(generationsField, new GridConstraints(3, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(200, 24), null, 0, false));
        OKButton = new JButton();
        OKButton.setText("OK");
        optionsPanel.add(OKButton, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(100, 24), null, 0, false));
        final Spacer spacer1 = new Spacer();
        optionsPanel.add(spacer1, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(234, 14), null, 0, false));
        speedSlider = new JSlider();
        speedSlider.setMaximum(200);
        speedSlider.setMinimum(50);
        speedSlider.setPaintLabels(false);
        speedSlider.setPaintTicks(false);
        speedSlider.setValue(100);
        speedSlider.setValueIsAdjusting(false);
        optionsPanel.add(speedSlider, new GridConstraints(4, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(200, 24), null, 0, false));
        speedLabel = new JLabel();
        speedLabel.setText("Speed");
        optionsPanel.add(speedLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.PLAIN, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("x0.5");
        optionsPanel.add(label1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setEnabled(true);
        Font label2Font = this.$$$getFont$$$(null, Font.PLAIN, -1, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("x2");
        optionsPanel.add(label2, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(17, 16), null, 0, false));
        stopButton = new JButton();
        stopButton.setText("Stop");
        optionsPanel.add(stopButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, new Dimension(100, 24), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        optionsPanel.add(scrollPane1, new GridConstraints(7, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 40), null, 0, false));
        output = new JTextArea();
        output.setEditable(false);
        scrollPane1.setViewportView(output);
        final Spacer spacer2 = new Spacer();
        optionsPanel.add(spacer2, new GridConstraints(6, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 20), null, 0, false));
        final Spacer spacer3 = new Spacer();
        optionsPanel.add(spacer3, new GridConstraints(8, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(234, 14), null, 0, false));
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(boardPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(194, 24), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
