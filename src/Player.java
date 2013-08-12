public abstract class Player {

    private  final String name;
    private final char side;

    public Player(String name, char side) {
        this.name = name;
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public char getSide() {
        return side;
    }

    public abstract PlayerStep performStep(Game game);
    public abstract void notifyAboutTurn();
}
