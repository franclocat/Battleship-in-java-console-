package game;

import java.util.Objects;
import java.util.Scanner;

public class battlefield {

    public static void main(String[] args) {
        String[][] boats = new String[][]{{"Aircraft Carrier", "5"}, {"Battleship","4"}, {"Submarine", "3"}, {"Cruiser", "3"}, {"Destroyer", "2"}};
        String[][] board = createBoard();
        printBoard(board);
        for (int i = 0; i < boats.length; i++) {
            System.out.println("Enter the coordinates of the " + boats[i][0] + " " + "(" + Integer.parseInt(boats[i][1]) + " cells):"); /*print the
             type of boats by using the index of the list and print teh amount of cells needed*/
            StringBuilder[] coordinates = getBoatCoordinates();
            int length = getLength(coordinates);
            if (length != Integer.parseInt(boats[i][1])) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                i -= 1;
            } else {
                if (!checkForAdjacentShips(board, coordinates, length)) {
                    String[][] updatedBoard = updateBoard(board, coordinates, length);
                    printBoard(updatedBoard);
                } else {
                    System.out.println("Error! You placed it too close to another one. Try again: \n");
                    i -= 1;
                }
            }
        }
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
            System.out.print((char) (65 + i) + " "); // print the letters on the left side of each row
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

    }

    private static StringBuilder[] getBoatCoordinates() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        StringBuilder[] coordinates = new StringBuilder[2];

        //Make a loop that keeps prompting for coordinates if the input given is not valid
        while (!valid) {
            StringBuilder ShipStartCoordinates = new StringBuilder(scanner.next());
            StringBuilder ShipEndCoordinates = new StringBuilder(scanner.next());
            coordinates[0] = ShipStartCoordinates;
            coordinates[1] = ShipEndCoordinates;


            //check for a valid length of the input
            if (ShipStartCoordinates.length() < 2 || ShipStartCoordinates.length() > 3 || ShipEndCoordinates.length() < 2 || ShipEndCoordinates.length() > 3) {
                System.out.println("Error: The input has to look like \"A5 A10 or A5 B5\"!\n");
                continue;
            }
            //divide the coordinates in x(columns) and y(rows) y stripping the letter and teh numbers apart
            String rowFirst = String.valueOf(ShipStartCoordinates.charAt(0));
            String rowSecond = String.valueOf(ShipEndCoordinates.charAt(0));
            String colFirst = String.valueOf(ShipStartCoordinates.substring(1,ShipStartCoordinates.length()));
            String colSecond = String.valueOf(ShipEndCoordinates.substring(1,ShipEndCoordinates.length()));

            //define valid values for rows
            String validRows = "ABCDEFGHIJ";

            if (validRows.contains(rowFirst) && validRows.contains(rowSecond) && colFirst.matches("[1-9]|10") &&
            colSecond.matches("[1-9]|10")) {
                //check for horizontal positioning
                if (rowFirst.equals(rowSecond) && !colFirst.equals(colSecond)) {
                    valid = true;
                } else if (colFirst.equals(colSecond) && !rowFirst.equals(rowSecond)) { //check for horizontal positioning
                    valid = true;
                } else {
                    System.out.println("Error! Wrong ship location! Try again: \n");
                }
            } else {
                System.out.println("Error! Wrong ship location! Try again:");
            }


        }
        return coordinates;
    }

    private static int getLength(StringBuilder[] coordinates) {
        int length = 0;
        //can be optimized and get deleted by only using coordinates.charAt() comparison
        String rowFirstCoordinate = String.valueOf(coordinates[0].charAt(0));
        String rowSecondCoordinate = String.valueOf(coordinates[1].charAt(0));
        String columnFirstCoordinate= String.valueOf(coordinates[0].substring(1,coordinates[0].length()));
        String columnSecondCoordinate = String.valueOf(coordinates[1].substring(1,coordinates[1].length()));
        //
        if (rowFirstCoordinate.equals(rowSecondCoordinate) && !columnFirstCoordinate.equals(columnSecondCoordinate )) {
            length = Math.abs(Integer.parseInt(columnFirstCoordinate) - Integer.parseInt(columnSecondCoordinate )) + 1;
            System.out.println("Length: " + length);
        } else if (columnFirstCoordinate.equals(columnSecondCoordinate ) && !rowFirstCoordinate.equals(rowSecondCoordinate)) { //check for horizontal positioning
            // get the length by subtracting the value of the rows as a char
            length = Math.abs(coordinates[0].charAt(0) - coordinates[1].charAt(0)) + 1;
            System.out.println("Length: " + length);
        }
        return length;
    }

    private static String[][] updateBoard(String[][] board, StringBuilder[] coordinates, int length) {
        //Ship first coordinates
        int yAxisStart = (int) coordinates[0].charAt(0) % 65;
        int xAxisStart = Integer.parseInt(coordinates[0].substring(1,coordinates[0].length()));
        //Ship second coordinates
        int yAxisEnd = (int) coordinates[1].charAt(0) % 65;
        int xAxisEnd = Integer.parseInt(coordinates[1].substring(1,coordinates[1].length()));

        if (yAxisEnd == yAxisStart && xAxisStart != xAxisEnd) {
            for (int i = 0; i < length; i++) {
                board[yAxisEnd][Math.min(xAxisStart, xAxisEnd) + i - 1] = "O";
            }
        } else if (xAxisStart == xAxisEnd && yAxisStart != yAxisEnd) { //check for horizontal positioning
            for (int i = 0; i < length; i++) {
                board[Math.min(yAxisStart, yAxisEnd) + i][xAxisStart - 1] = "O";
            }
        }
        return board;
    }

    private static boolean checkForAdjacentShips(String[][] board, StringBuilder[] coordinates, int length) {
        boolean adjacentShips = false;
        /////////////////////////////////////////////////////////////////////////////////////////
        //Ship first coordinates
        int yAxisStart = (int) coordinates[0].charAt(0) % 65;
        int xAxisStart = Integer.parseInt(coordinates[0].substring(1,coordinates[0].length()));
        //Ship second coordinates
        int yAxisEnd = (int) coordinates[1].charAt(0) % 65;
        int xAxisEnd = Integer.parseInt(coordinates[1].substring(1,coordinates[1].length()));
        /////////////////////////////////////////////////////////////////////////////////////////
        try {
            if (yAxisStart == yAxisEnd && xAxisStart != xAxisEnd) {
                for (int i = 0; i < length; i++) {
                    int xAxis = Math.min(xAxisStart, xAxisEnd) + i - 1;
                    if (Objects.equals(board[yAxisStart][xAxis], "O") || Objects.equals(board[yAxisStart + 1][xAxis], "O")
                            || Objects.equals(board[yAxisStart - 1][xAxis], "O")) {
                        adjacentShips = true;
                        break;
                    }
                }
            } else if (xAxisStart == xAxisEnd && yAxisStart != yAxisEnd) { //check for horizontal positioning
                for (int i = 0; i < length; i++) {
                    //TODO match the checking algorithm for vertical ships
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        return adjacentShips;
    }
}
