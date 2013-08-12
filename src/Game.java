
public class Game {

    private Player crossPlayer;
    private Player zeroPlayer;
    private Field field;


    public Game(Player crossPlayer, Player zeroPlayer, Field field) {

        this.crossPlayer = crossPlayer;
        this.zeroPlayer = zeroPlayer;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void declareWinner(Player player) {

        System.out.println("And the winner is " + player.getName() + ", who played as " + player.getSide());
    }

    public void declareDraw() {
        System.out.println("The game is draw");
    }

    public Player triggerPlayer(Player player) {
        if (player == this.crossPlayer) {
            return this.zeroPlayer;
        } else {
            return this.crossPlayer;
        }
    }
}
