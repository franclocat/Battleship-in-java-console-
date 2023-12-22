package game;

import java.util.*;

public class Player {
    String[][] playerBoard = createBoard();
    String[][] fogOfWar = createBoard();
    List <Ship> ships = new ArrayList<>();

    public static void printBoard(String[][] board) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print((char) (i + 65) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    void placeShips() {
        ships.add(new Ship("Aircraft Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Destroyer", 2));


        for (int i = 0; i < ships.size(); i++) {
            System.out.println("Enter the coordinates of the " + ships.get(i).name + " (" + ships.get(i).length + " cells)");
            int[][] shipCoordinates = validateTwoCoordinates();
            int[] start = shipCoordinates[0];
            int[] end = shipCoordinates[1];
            int length = getLength(start, end);
            if (length != ships.get(i).length) {
                System.out.println("Wrong ship length!");
                i -= 1;
            } else {
                String direction = isHorizontalOrVertical(start, end);
                ships.get(i).setDirection(direction);
                boolean adjacent = checkAdjacency(start, end, direction);
                if (!adjacent) {
                    updateShipsInBoard(i, length, shipCoordinates);
                    printBoard(playerBoard);
                } else {
                    System.out.println("Error: Too close to another ship!");
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

    private int[][] validateTwoCoordinates() {
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
                try {
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
                } catch (NumberFormatException e) {
                    System.out.println("Error: Wrong coordinate input\n");
                }
            }
        }
        return checkedCoordinates;
    }

    private int getLength(int[] start, int[] end) {
        int length = 0;
        if (start[0] == end[0] && start[1] != end[1]) {
            length = Math.abs(start[1] - end[1]) + 1; // the 1 is added because we count all the coordinates as part of the ship
        } else if (start[1] == end[1] && start[0] != end[0]) {
            length = Math.abs(start[0] - end[0]) + 1;
        }
        return length;
    }

    private String isHorizontalOrVertical(int[] start, int[] end) {
        String direction = "";
        if (start[0] == end[0]) {
            direction = "horizontal";
        } else if (start[1] == end[1]) {
            direction = "vertical";
        }
        return direction;
    }

    private boolean checkAdjacency(int[] start, int[] end, String direction) {
        boolean adjacentShips = false;

        if (direction.equals("horizontal")) {
            int smallestX = Math.min(start[1], end[1]);
            int largestX = Math.max(start[1], end[1]);

            // Adjust bounds to prevent out-of-bounds access
            int minX = Math.max(smallestX - 1, 0);
            int maxX = Math.min(largestX + 1, playerBoard[0].length - 1);
            int minY = Math.max(start[0] - 1, 0);
            int maxY = Math.min(start[0] + 1, playerBoard.length - 1);

            for (int i = minY; i <= maxY; i++) {
                for (int j = minX; j <= maxX; j++) {
                    if (playerBoard[i][j].equals("O")) {
                        adjacentShips = true;
                        break;
                    }
                }
                if (adjacentShips) {
                    break;
                }
            }
        } else if (direction.equals("vertical")) {
            int smallestY = Math.min(start[0], end[0]);
            int largestY = Math.max(start[0], end[0]);

            // Adjust bounds to prevent out-of-bounds access
            int minX = Math.max(start[1] - 1, 0);
            int maxX = Math.min(start[1] + 1, playerBoard[0].length - 1);
            int minY = Math.max(smallestY - 1, 0);
            int maxY = Math.min(largestY + 1, playerBoard.length - 1);

            for (int i = minY; i <= maxY; i++) {
                for (int j = minX; j <= maxX; j++) {
                    if (playerBoard[i][j].equals("O")) {
                        adjacentShips = true;
                        break;
                    }
                }
                if (adjacentShips) {
                    break;
                }
            }
        }
        return adjacentShips;
    }

    private void updateShipsInBoard(int shipNumber, int length, int[][] coordinates) {
        int[] shipPart = new int[2];
        List<int[]> allShipParts = new ArrayList<>();
        if (ships.get(shipNumber).getDirection().equals("vertical")) {
            int smallerY = Math.min(coordinates[0][0], coordinates[1][0]);
            for (int i = 0; i < length; i++) {
                playerBoard[smallerY + i][coordinates[0][1]] = "O";
                shipPart[0] = smallerY + i;
                shipPart[1] = coordinates[0][1];
                allShipParts.add(shipPart);
            }
        } else if (ships.get(shipNumber).getDirection().equals("horizontal")) {
            int smallerX = Math.min(coordinates[0][1], coordinates[1][1]);
            for (int i = 0; i < length; i++) {
                playerBoard[coordinates[0][0]][smallerX + i] = "O";
                shipPart[0] = coordinates[0][0];
                shipPart[1] = smallerX + i;
                allShipParts.add(shipPart);
            }
        }
        ships.get(shipNumber).setShipParts(allShipParts);
    }

    private static int[] validateHitCoordinates() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        int[] checkedCoordinates = new int[2];

        while (!valid) {
            StringBuilder hitCoordinates = new StringBuilder(scanner.next());
            if (hitCoordinates.length() < 2 || hitCoordinates.length() > 3) {
                System.out.println("Error: Wrong coordinate input\n");
            } else {
                checkedCoordinates[0] = hitCoordinates.charAt(0) % 65; // A = 0, B = 1, C = 2...
                checkedCoordinates[1] = Integer.parseInt(hitCoordinates.substring(1,hitCoordinates.length())) - 1; //A10 -> 10 -> 9

                if (checkedCoordinates[0] > 9 || checkedCoordinates[1] < 0 || checkedCoordinates[1] > 9) {
                    System.out.println("Error: Wrong coordinates!");
                } else {
                    valid = true;
                }
            }
        }
        return checkedCoordinates;
    }
    private boolean checkHit(int[] fireCoordinates, String[][] otherPlayersBoard) {
        boolean hit = false;
        if (otherPlayersBoard[fireCoordinates[0]][fireCoordinates[1]].equals("O") || otherPlayersBoard[fireCoordinates[0]][fireCoordinates[1]].equals("X")) {
            playerBoard[fireCoordinates[0]][fireCoordinates[1]] = "X";
            fogOfWar[fireCoordinates[0]][fireCoordinates[1]] = "X";
            hit = true;
        } else {
            playerBoard[fireCoordinates[0]][fireCoordinates[1]] = "M";
            fogOfWar[fireCoordinates[0]][fireCoordinates[1]] = "M";
        }
        return hit;
    }

    private boolean isShipSunk(List<Ship> enemyShips, String[][] enemyBoard) {
        boolean sunkenShip = false;
        for (Ship ship : enemyShips) {
            Iterator<int[]> iterator = ship.shipParts.iterator();
            while (iterator.hasNext()) {
                int[] part = iterator.next();
                if (enemyBoard[part[0]][part[1]].equals("X")) {
                    iterator.remove();
                    if (ship.shipParts.isEmpty()) {
                        enemyShips.remove(ship);
                        sunkenShip = true;
                        break;
                    }
                }
            }
            if (sunkenShip) {
                break;
            }
        }
        return sunkenShip;
    }

    private boolean isWin(String[][] enemyBoard) {

        boolean isWin = true;
        for (String[] rows : enemyBoard) {
            for (String column : rows) {
                if (column.equals("O")) {
                    isWin = false;
                    break;
                }
            }
        }
        return isWin;
    }

    //TODO only check one hit at a time
    void shootTheShips(String[][] enemyBoard, List<Ship> enemyShips) {
        printBoard(fogOfWar);
        System.out.println("Take a shot!");
        while(!isWin(enemyBoard)) {
            int[] fireCoordinates = validateHitCoordinates();
            boolean hit = checkHit(fireCoordinates, enemyBoard);
            printBoard(fogOfWar);
            if (isShipSunk(enemyShips, enemyBoard)) {
                System.out.println("You sank a ship! ");
            } else {
                if (hit) {
                    System.out.println("You hit a ship! Try again:\n");
                } else {
                    System.out.println("You missed. Try again:\n");
                }
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}
