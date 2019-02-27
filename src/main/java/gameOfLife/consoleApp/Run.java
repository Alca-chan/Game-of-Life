package gameOfLife.consoleApp;

import gameOfLife.Board;

import java.util.Scanner;

public class Run {
    public void run() {
        int height;
        int width;
        int numOfGenerations;

        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------");
        System.out.println("WELCOME to the GAME OF LIFE");
        System.out.println("---------------------------");
        System.out.print("Height of the board: ");
        height = scanner.nextInt();
        System.out.print("Width of the board: ");
        width = scanner.nextInt();
        System.out.print("Number of iterations (generations): ");
        numOfGenerations = scanner.nextInt();
        scanner.close();
        System.out.println("---------------------------");

        Board board = new Board(height, width);
        board.setRandomBoard();
        System.out.println(board.toString());

        for (int i = 0; i <= numOfGenerations; i++) {
            board.update();
            System.out.println(board.toString());
        }
    }
}
