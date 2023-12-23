package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();

        System.out.println("Player 1, place your ships on the game field\n");
        Player.printBoard(player1.playerBoard);
        player1.placeShips();
        System.out.println("Press Enter and pass the move to another player\n...");
        //TODO detect when enter is pressed instead of an input
        scanner.next();
        System.out.println("Player 2, place your ships on the game field\n");
        Player.printBoard(player2.playerBoard);
        player2.placeShips();
        System.out.println("Press Enter and pass the move to another player\n...");
        scanner.next();

        boolean win = false;
        while (!win) {
            win = player1.shootTheShips(player2.playerBoard, player2.ships, win);
            System.out.println("Player 1, it's your turn:\n");
            win = player2.shootTheShips(player1.playerBoard, player1.ships, win);
            System.out.println("Player 2, it's your turn:\n");
        }
    }
}
