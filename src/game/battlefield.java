package game;

import java.util.Scanner;

public class battlefield {

    public static void main(String[] args) {
        String[][] board = createBoard();
        printBoard(board);
        getBoatCoordinates();
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

    private static StringBuilder getBoatCoordinates() throws NullPointerException{
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        StringBuilder firstCoords = new StringBuilder();
        StringBuilder secondCoords = new StringBuilder();

        //Make a loop that keeps prompting for coordinates if the input given is not valid
        while (!valid) {
            System.out.println("Enter the coordinates of the ship (start and end):");
            firstCoords.append(scanner.next());
            secondCoords.append(scanner.next());

            //check for a valid length for the input without knowing if the given values are valid
            if (firstCoords.length() < 2 || firstCoords.length() > 3 || secondCoords.length() < 2 || secondCoords.length() > 3) {
                System.out.println("Error: The input has to look like \"A5 A10\"!\n");
                continue;
            }
            //divide the coordinates in rows and columns for later validation
            String rowFirst = String.valueOf(firstCoords.charAt(0));
            String rowSecond = String.valueOf(secondCoords.charAt(0));
            String colFirst = String.valueOf(firstCoords.substring(1,firstCoords.length()));
            String colSecond = String.valueOf(secondCoords.substring(1,secondCoords.length()));

            //define valid values for rows
            String validRows = "ABCDEFGHIJ";

            if (validRows.contains(rowFirst) && validRows.contains(rowSecond) && colFirst.matches("[1-9]|10") &&
            colSecond.matches("[1-9]|10")) {
                if (rowFirst.equals(rowSecond) && !colFirst.equals(colSecond)) {
                     int sum = Math.abs(Integer.parseInt(colFirst) - Integer.parseInt(colSecond)) + 1;
                     System.out.println("Length: " + sum);
                     valid = true;
                } else if (colFirst.equals(colSecond) && !rowFirst.equals(rowSecond)) {
                    int sum = Math.abs(firstCoords.charAt(0) - secondCoords.charAt(0)) + 1;
                    System.out.println("Length: " + sum);
                    valid = true;
                }
            } else {
                System.out.println("Error: The coordinates have to be between [A-J] and [1-10]!");
            }


        }
        return firstCoords;
    }

}
