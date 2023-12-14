package game;

import java.util.Objects;
import java.util.Scanner;

public class battlefield {

    public static void main(String[] args) {
        String[][] boardPlayerOne = createBoard();
        printBoard(boardPlayerOne);
        placeShips(boardPlayerOne);
        System.out.println("The game starts!");
        //temporary shooting without loop & with own board
        printBoard(boardPlayerOne);
        System.out.println("Take a shot!");
        int[] shotCoordinates = validateCoordinates();
        boolean hit = checkHit(boardPlayerOne,shotCoordinates);
        printBoard(boardPlayerOne);
        if (hit) {
            System.out.println("You hit a ship!");
        } else {
            System.out.println("You missed!");
        }
        //////////////////////////////////////////////////
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

    private static void updateShipsInBoard(String[][] board, int[] firstCoordinate, int[]secondCoordinate, int length) {
        if (firstCoordinate[0] == secondCoordinate[0]) {
            int smallestXAxis = Math.min(firstCoordinate[1], secondCoordinate[1]);
            for (int i = 0; i < length; i++) {
                board[firstCoordinate[0]][smallestXAxis + i] = "O";
            }
        } else if (firstCoordinate[1] == secondCoordinate[1]) {
            int smallestYAxis = Math.min(firstCoordinate[0], secondCoordinate[0]);
            for (int i = 0; i < length; i++) {
                board[smallestYAxis + i][firstCoordinate[1]] = "O";
            }
        }
    }

    private static int[][] validateTwoCoordinates() {
        Scanner scanner = new Scanner(System.in);
        int[][] checkedCoordinates = new int[2][2];
        boolean valid = false;

        while (!valid) {
            StringBuilder start = new StringBuilder(scanner.next());
            StringBuilder end = new StringBuilder(scanner.next());

            if (start.length() < 2 || start.length() > 3 || end.length() < 2 || end.length() > 3) {
                System.out.println("Error: Wrong coordinate input\n");
            } else {
                //divide the inputs in X and Y coordinates
                checkedCoordinates[0][0] = start.charAt(0) % 65; // A = 0, B = 1, C = 2...
                checkedCoordinates[0][1] = Integer.parseInt(start.substring(1,start.length())) - 1; //A10 -> 10 -> 9

                checkedCoordinates[1][0] = end.charAt(0) % 65;
                checkedCoordinates[1][1] = Integer.parseInt(end.substring(1,end.length())) - 1;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        if (checkedCoordinates[i][j] > 9|| checkedCoordinates[i][j] < 0) {
                            System.out.println("Error: Wrong coordinates!");
                            break;
                        } else if (i == 1) {
                            valid = true;
                        }
                    }
                }
            }
        }
        return checkedCoordinates;
    }

    private static int[] validateCoordinates() {
        Scanner scanner = new Scanner(System.in);
        int[] checkedCoordinates =  new int[2];

        boolean valid = false;

        while (!valid) {
            StringBuilder nonCheckedCoordinates = new StringBuilder(scanner.next());
            if (nonCheckedCoordinates.length() < 2 || nonCheckedCoordinates.length() > 3) {
                System.out.println("\"Error, wrong input!\n");
                continue;
            } else {
                try {
                    char coordinatesYAxis = nonCheckedCoordinates.charAt(0);
                    int coordinatesXAxis = Integer.parseInt(String.valueOf(nonCheckedCoordinates.substring(1,nonCheckedCoordinates.length())));
                    String validRows = "ABCDEFGHIJ";
                    if (validRows.contains(String.valueOf(coordinatesYAxis)) && coordinatesXAxis > 0 && coordinatesXAxis < 11) {
                        checkedCoordinates[0] = coordinatesYAxis % 65; //adds the index value of the Y-Axis to the checkedCoordinates
                        checkedCoordinates[1] = coordinatesXAxis - 1;  //adds the index value of the X-Axis to the checkedCoordinates
                        valid = true;
                    } else {
                        System.out.println("Error! Wrong ship location! Try again:");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error! Wrong ship location! Try again:");
                }

            }
        }
        return checkedCoordinates;
    }

    private static boolean checkHorizontalOrVertical(int[] first, int[] second) {
        return !((first[0] != second[0]) && first[1] != second[1]);
    }

    private static int getLength(int[] first, int[] second) {
        int length = 0;

        if (first[0] == second[0] && first[1] != second[1]) {
            length = Math.abs(first[1] - second[1]) + 1;
        } else if (first[1] == second[1] && first[0] != second[0]) {
            length = Math.abs(first[0] - second[0]) + 1;
        }
        return length;
    }

    private static boolean validateAdjacency (String[][] board, int[] firstCoordinate, int[] secondCoordinate, int length) {
        boolean adjacentShips = false;
        try {
            if (firstCoordinate[0] == secondCoordinate[0]) {
                int smallestXAxis = Math.min(firstCoordinate[1], secondCoordinate[1]);
                for (int i = 0; i < length + 2; i++) {
                    if (board[firstCoordinate[0]][smallestXAxis + i - 1].equals("O")
                            ||board[firstCoordinate[0] + 1][smallestXAxis + i - 1].equals("O")
                            || board[firstCoordinate[0] - 1][smallestXAxis + i - 1].equals("O")) {
                        adjacentShips = true;
                        break;
                    }
                }
            } else if (firstCoordinate[1] == secondCoordinate[1]) {
                for (int i = 0; i < length + 2; i++) {
                    int smallestYAxis = Math.min(firstCoordinate[0], secondCoordinate[0]);
                    if (board[smallestYAxis + i - 1][firstCoordinate[1]].equals("O")
                            || board[smallestYAxis + i - 1][firstCoordinate[1] + 1].equals("O")
                            || board[smallestYAxis + i - 1][firstCoordinate[1] - 1].equals("O")) {
                        adjacentShips = true;
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {}
        return adjacentShips;
    }

    private static void placeShips(String[][] board) {
        String[][] boats = new String[][]{
                {"Aircraft Carrier", "5"},
                {"Battleship","4"},
                {"Submarine", "3"},
                {"Cruiser", "3"},
                {"Destroyer", "2"}};

        for (int i = 0; i < boats.length; i++) {
            System.out.println("Enter the coordinates of the " + boats[i][0] + " " + "(" + Integer.parseInt(boats[i][1]) + " cells):");
            //int[] firstCoordinate = validateCoordinates();
            //int[] secondCoordinate = validateCoordinates();
            int[][] coordinates = validateTwoCoordinates();
            int[] firstCoordinate = coordinates[0];
            int[] secondCoordinate = coordinates[1];

            int length = getLength(firstCoordinate, secondCoordinate);
            if (!checkHorizontalOrVertical(firstCoordinate, secondCoordinate)) {
                System.out.println("Error! Wrong ship location! Try again: \n");
                i -= 1;
            } else if (length != Integer.parseInt(boats[i][1])) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                i -= 1;
            } else if(validateAdjacency(board, firstCoordinate, secondCoordinate, length)) {
                System.out.println("Error! You placed it too close to another one. Try again: \n");
                i -= 1;
            } else {
                updateShipsInBoard(board, firstCoordinate, secondCoordinate, length);
                printBoard(board);
            }
        }

    }

    private static boolean checkHit(String[][] board, int[] shotCoordinates) {
        boolean hit = false;
        if (board[shotCoordinates[0]][shotCoordinates[1]].equals("O")) {
            board[shotCoordinates[0]][shotCoordinates[1]] = "X";
            hit = true;
        } else {
            board[shotCoordinates[0]][shotCoordinates[1]] = "M";
        }
        return hit;
    }

    //coordinate validator TEMPORARY

}
