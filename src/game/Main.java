package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();
        Player playerTurn = player1;


        System.out.println("Player 1, place your ships on the game field");
        Player.printBoard(playerTurn.playerBoard);
        player1.placeShips();
        System.out.println("Press Enter and pass the move to another player\n...");
        scanner.next();
        System.out.println("Player 2, place your ships on the game field");
        Player.printBoard(player2.playerBoard);
        player2.placeShips();
        System.out.println("Press Enter and pass the move to another player\n...");
        scanner.next();

        System.out.println("Player 1, it's your turn:");
        player1.shootTheShips(player2.playerBoard, player2.ships);
    }
}
