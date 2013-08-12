import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean tempResult; // variables to store if the win was the result of step
        Player tempPlayer; // stores temp player. Will be used in the game cycle.

        Scanner sc = new Scanner(System.in);

        System.out.println("Cross player, enter your name, please");
        HumanPlayer crossPlayer = new HumanPlayer(sc.nextLine(), 'X');
        System.out.println("Zero player, enter your name, please");
        HumanPlayer zeroPlayer = new HumanPlayer(sc.nextLine(), '0');

        Field gameField = new Field();
        Game game = new Game(crossPlayer, zeroPlayer, gameField);

        gameField.eraseField();
        gameField.drawField();

        tempPlayer = crossPlayer;

        do {
            tempResult = gameField.handleStep(tempPlayer.performStep(game), game);
            tempPlayer = game.triggerPlayer(tempPlayer);

        } while (!tempResult);

    }

}
