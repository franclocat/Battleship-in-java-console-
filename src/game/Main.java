package game;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();

        Player.printBoard(player1.playerBoard);
        player1.placeShips();
        player1.shootTheShips();
    }
}
