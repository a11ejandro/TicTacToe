import java.util.Scanner;

public class HumanPlayer extends Player {

    private static final String UNDO_STRING = "u";
    private static Scanner lineReader = new Scanner(System.in);

    public HumanPlayer(String name, char side) {
        super(name, side);
    }

    @Override
    public PlayerStep performStep(Game game) {
        int x;
        int y;
        String choice;
        boolean wasUndone = false;                                  //stores true if user denied his step;
        Field field = game.getField();
        field.drawField();
        notifyAboutTurn();
        System.out.println("Enter the coordinates of cell that you want to fill in [x, y] or 'u' to undo your last step");

        choice = askCorrectInput(wasUndone);

        if (choice.equals(UNDO_STRING)) {
                game.getField().undoLastStep();
                wasUndone = true;
                choice = askCorrectInput(wasUndone);
        }

        // Displayed field is rotated by 90 degrees
        y = Integer.parseInt(choice);
        wasUndone = true;
        x = Integer.parseInt(askCorrectInput(wasUndone));

        while (field.areWrongCoordinates(x, y)) {
            System.out.println("Error! Cell with such coordinates is busy or doesn't exist.");
            System.out.println("Enter the coordinates of cell that you want to fill in [x, y]");

            y = Integer.parseInt(askCorrectInput(wasUndone));
            x = Integer.parseInt(askCorrectInput(wasUndone));
        }

        return new PlayerStep(x, y, this);

    }

    @Override
    public void notifyAboutTurn() {
        System.out.println("Now it's " + getName() + "'s (" + getSide() + ") turn");
    }

    private boolean isWrongInput(String input, boolean wasUndone) {
        if (wasUndone) {
             return !isDigitInput(input);
        } else {
            return !(input.equals(UNDO_STRING)) || isDigitInput(input);
        }

    }

    private boolean isDigitInput(String input) {
        try {
            Integer.parseInt(input);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    private String askCorrectInput(boolean wasUndone) {
        String inputString = lineReader.nextLine();

        while (isWrongInput(inputString, wasUndone)) {
            System.out.println("Sorry, your input is wrong. Retry, please.");
            inputString = lineReader.nextLine();
        }
        return inputString;
    }


}
