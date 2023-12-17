package game;

import java.util.Scanner;

public class Player {
    String[][] playerBoard = createBoard();
    String[][] fogOfWar = createBoard();
    private static String[][] createBoard() {
        String[][] board = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = "~";
            }
        }
        return board;
    }
}
