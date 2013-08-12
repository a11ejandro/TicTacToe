public class Field {

    private static final char DEFAULT_CELL_VALUE = ' ';
    private static final int DEFAULT_FIELD_SIZE = 3;

    private final int fieldSize;

    private char[][] field;
    private int numberOfEmptyCells;
    private int movesTotal = 0;
    private PlayerStep[] moves;                             //stores moves

    public Field() {
       this(DEFAULT_FIELD_SIZE);
    }

    public Field(int size) {
        fieldSize = size;
        field = new char[fieldSize][fieldSize];
        numberOfEmptyCells = fieldSize*fieldSize;
        moves = new PlayerStep[numberOfEmptyCells];
    }


    public boolean areWrongCoordinates(int x, int y) {   //This method will be mostly used by HumanPlayer
        if (x < fieldSize && y < fieldSize) {
            if (field[x][y] == DEFAULT_CELL_VALUE) {
                return false;
            }
        }
        return true;
    }

    public void eraseField() {
        for(int i = 0; i < fieldSize; i++) {
            eraseRow(i);
        }
    }

    public void drawField() {
        //draws numbers of columns
        System.out.print("  ");
        for(int i = 0; i < fieldSize; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.println();

        for(int y = 0; y < fieldSize; y++) {
            drawRow(y); //numbers of rows are also drawn here
        }

        System.out.println("Step # " + movesTotal);
    }

    private boolean isHorizontalWinner(Player player) {

        char side = player.getSide();
        for( int x = 0; x < fieldSize; x++) {

            int entries = 0;

            for( int y = 0; y < fieldSize; y++) {
                if (field[x][y] == side) {
                    entries++;
                }
            }

            if (enoughToWin(entries)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVerticalWinner(Player player) {
        for (int y = 0; y < fieldSize; y++) {

            char side = player.getSide();
            int entries = 0;

            for (int x = 0; x < fieldSize; x++) {
                if (field[x][y] == side) {
                    entries++;
                }
            }

            if (enoughToWin(entries)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDiagonalWinner(Player player) {

        char side = player.getSide();
        int x = 0;
        int y = 0;
        int entries = 0;

        //Checks uprise diagonal (x = y)
        while(x < fieldSize && y < fieldSize) {
            if (field[x][y] == side) {
                entries++;
            }
            x++;
            y++;
        }

        if (enoughToWin(entries)) {
            return true;
        }

        //reset counters
        x = entries = 0;
        y = fieldSize - 1;

        //checks descending diagonal (x = fieldSize - y)
        while(x < fieldSize && y >= 0) {
            if (field[x][y] == side) {
                entries++;
            }
            x++;
            y--;
        }

        return enoughToWin(entries);
    }

    public boolean handleStep(PlayerStep playerStep, Game game) {
        /*
        Method is called after each move
        Returns true if the match is finished
         */
        Player player = playerStep.getOwner();

        if (!areWrongCoordinates(playerStep.getX(), playerStep.getY())) {

            moves[movesTotal] = playerStep;             // add move to history
            movesTotal++;                               //and increase moves counter
            numberOfEmptyCells--;                       //decrease number of empty cells after each move;

           field[playerStep.getX()][playerStep.getY()] = playerStep.getOwner().getSide();

            if (isVerticalWinner(player) || isHorizontalWinner(player) || isDiagonalWinner(player)) {
                game.declareWinner(player);
                return true;
            }
            else if (numberOfEmptyCells == 0) {
                game.declareDraw();
                return true;
            }
        }
        return false;
    }

    public void undoLastStep() {
        if (movesTotal > 1) { //checks if there is at least 1 move from every side
            undoSideStep();
            undoSideStep();
            drawField();
        }
    }

    private void drawRow(int numberOfRow) {    //draws row with it's number
        System.out.print(numberOfRow + " ");
        for(int i = 0; i < fieldSize; i++)  {
            drawCell(numberOfRow, i);
        }
        System.out.println();
    }

    private void drawCell(int numberOfRow, int numberOfColumn) {
        System.out.print("[" + field[numberOfRow][numberOfColumn] + "]");
    }

    private void eraseRow(int numberOfRow) {
        for(int i = 0; i < fieldSize; i++) {
            eraseCell(numberOfRow, i);
        }
    }

    private void eraseCell(int numberOfRow, int numberOfColumn) {
        field[numberOfRow][numberOfColumn] = DEFAULT_CELL_VALUE;
    }

    private boolean enoughToWin(int entries) {
        return entries == fieldSize;


    }

    private void undoSideStep() {
        PlayerStep currentMove = moves[movesTotal-1];
        System.out.println("Removing at coordinates " + currentMove.getX() + " " + currentMove.getY());
        field[currentMove.getX()][currentMove.getY()] = DEFAULT_CELL_VALUE;
        moves[movesTotal-1] = null;
        movesTotal--;
        numberOfEmptyCells++;
    }

}

