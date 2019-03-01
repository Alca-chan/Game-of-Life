package gameOfLife.swingApp;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.*;

public class Main {
    private static final int HEIGHT = 500;
    private static final int WIDTH = 800;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game of Life");
        frame.setContentPane(new MainPanel().getPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setVisible(true);
    }
}
