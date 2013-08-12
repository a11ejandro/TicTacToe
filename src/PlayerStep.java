public class PlayerStep {
    private int x;
    private int y;
    private Player owner;

    public PlayerStep(int x, int y, Player owner) {
        this.y = y;
        this.owner = owner;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getOwner() {
        return owner;
    }
}
