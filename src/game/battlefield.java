package game;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class battlefield {

    public static void main(String[] args) {
        String[][] board = createBoard();
        printBoard(board);
        promptCoordinates();
    }
    private static String[][] createBoard() {
        String[][] board = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = "~";
            }
        }
        return board;
    }

    private static void printBoard(String[][] board) {
        System.out.print("  ");
        // print the numbers on top of the board
        for (int i = 1; i < 11; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        //print every value of the created board in the 10x10 grid
        for (int i = 0; i < 10; i++) {
            System.out.print((char) (65 +i) + " "); // print the letters on the left side of each row
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }

    private static StringBuilder promptCoordinates() {
        System.out.println("Enter the coordinates of the ship (just the start and end):");
        Scanner scanner = new Scanner(System.in);
        StringBuilder coordinates = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            if (scanner.hasNext()) {
                coordinates.append(scanner.next());
            }
        }
        System.out.println(coordinates);
        return coordinates;
    }

}
