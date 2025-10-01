package Ljuslin.com;

import java.util.Random;
import java.util.Scanner;

public class Methods {
    private final Scanner scanner;
    private char[] board;
    char playersPiece;
    char computersPiece;
    private final Printouts print;
    private final int allLines = 8;

    /**
     * Constructor, instantiates variables
     */
    public Methods() {
        scanner = new Scanner(System.in);
        print = new Printouts();
        playersPiece = 'O';
        computersPiece = 'X';
    }

    /**
     * Starts a new game
     */
    protected void startNewGame() {
        board = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //setPlayersPiece();
        print.printBoard(board);
        boolean goOn;
        while (true) {
            goOn = true;
            while (goOn) {
                goOn = !playerMove(board);
            }
            print.printBoard(board);
            if (hasWon(board, playersPiece)) {
                print.playerWon();
                break;
            }
            computerMove(board);
            if (hasWon(board, computersPiece)) {
                print.printBoard(board);
                print.computerWon();
                break;
            }
            print.printBoard(board);
        }
    }

    /**
     * Counts how many pieces the computer has on the board and goes to appropriate method
     * @param board, the game
     */
    private void computerMove(char[] board) {
        int count = countPieces(board, computersPiece);
        switch (count) {
            case 0 -> computerMakesFirstMove(board);
            case 1 -> computerMakesSecondMove(board);
            case 2 -> computerMakesThirdMove(board);
            case 3 -> computerMakesMoreMove(board);
        }
    }

    /**
     * Computer makes the first move
     * @param board,         the ongoing game
     */
    private void computerMakesFirstMove(char[] board) {
        if (board[4] == playersPiece) {
            randomMove(board, computersPiece, playersPiece);
        } else {
            board[4] = computersPiece;
        }
    }

    /**
     * Computer places the second piece, checks if player has two in a row
     * @param board, the game
     */
    private void computerMakesSecondMove(char[] board) {
        int move = computerMoveToBlock(board);
        int i = computerMoveToWin(board);
        if ( i != -1) {
            move = i;
        }
        if (move == -1) {
            randomMove(board, computersPiece, playersPiece);
        } else {
            makeMove(board, computersPiece, move + 1);
        }
    }

    /**
     * Computer places third piece, checks if it can win or if it has to block
     * @param board,the game
     */
    private void computerMakesThirdMove(char[] board) {
        int move = computerMoveToBlock(board);
        int i = computerMoveToWin(board);
        if ( i != -1) {
            move = i;
        }
        if (move == -1) {
            randomMove(board, computersPiece, playersPiece);
        } else {
            makeMove(board, computersPiece, move + 1);
        }
    }

    /**
     * When the computer has three pieces on the board and has to figure out which piece to move
     * where.
     * 1, checks if it can win
     * 2, checks which piece it can move without letting player win
     * 3, checks if it has to block
     *      otherwise random
     * @param board, the game
     */
    private void computerMakesMoreMove(char[] board) {
        int piece1 = -1;
        int piece2 = -1;
        int piece3 = -1;
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == computersPiece && count == 0) {
                piece1 = i;
                count++;
            } else if (board[i] == computersPiece && count == 1) {
                piece2 = i;
                count++;
            } else if (board[i] == computersPiece && count == 2){
                piece3 = i;
                count++;
            }
        }
        int[] winMove = computerMoveAndWin(board);
        if (winMove[0] != -1) {
            int one = winMove[0];
            int two = winMove[1];
            if (piece1 == one) {
                if (piece2 == two)
                    removePiece(board, piece3 + 1);
                else if (piece3 == two)
                    removePiece(board, piece2 + 1);
            } else if (piece2 == one) {
                if (piece1 == two)
                    removePiece(board, piece3 + 1);
                else if (piece3 == two)
                    removePiece(board, piece1 + 1);
            } else { //piece 3 == one
                if (piece1 == two)
                    removePiece(board, piece2 + 1);
                else if (piece2 == two)
                    removePiece(board, piece1 + 1);
            }
        } else {
            int[] notToMove1 = {-1, -1};
            int[] notToMove2 = {-1, -1};
            int i = 0;
            notToMove1 = computerNotToMove(board, i);

            if (notToMove1[0] != -1) {
                i = notToMove1[1];
                notToMove2 = computerNotToMove(board, i + 1);
            }
            int notMove1 = notToMove1[0], notMove2 = notToMove2[0];
            if (notMove1 != -1 && notMove2 != -1) {
                if (piece1 == notMove1) {
                    if (piece2 == notMove2)
                        removePiece(board, piece3 + 1);
                    else if (piece3 == notMove2)
                        removePiece(board, piece2 + 1);
                } else if (piece2 == notMove1) {
                    if (piece1 == notMove2)
                        removePiece(board, piece3 + 1);
                    else if (piece3 == notMove2)
                        removePiece(board, piece1 + 1);
                } else { //piece 3 == notMove1
                    if (piece1 == notMove2)
                        removePiece(board, piece2 + 1);
                    else if (piece2 == notMove2)
                        removePiece(board, piece1 + 1);
                }
            } else if (notMove1 != -1 && notMove2 == -1) {
                if (piece1 == notMove1)
                    removePiece(board, piece2 + 1);
                else if (piece2 == notMove1)
                    removePiece(board, piece3 + 1);
                else
                    removePiece(board, piece1 + 1);
            } else {
                removePiece(board, piece1 + 1);
            }
        }
        int moveTo = computerMoveToBlock(board);
        int moveToWin = computerMoveToWin(board);
        if (moveToWin != -1)
            moveTo = moveToWin;
        if (moveTo == -1) {
            randomMove(board, computersPiece, playersPiece);
        } else {
            makeMove(board, computersPiece, moveTo + 1);
        }
    }

    /**
     * Checks if computer needs to block
     * @param board the game
     * @return int, the field to move to (0-8) -1 if no field found
     */
    private int computerMoveToBlock(char[] board) {
        for (int i = 0; i < allLines; i++) {
            String line = getLine(board, i);
            int countO = 0;
            int countX = 0;
            char[] cLine = line.toCharArray();
            for (int k = 0; k < cLine.length; k++) {
                if (cLine[k] == 'O')
                    countO++;
                else if (cLine[k] == 'X') {
                    countX++;
                }
            }
            if (countO == 2 && countX == 0) {
                if (cLine[0] == cLine[1]) {
                    switch (i) {
                        case 0 -> {
                            return 2;
                        }
                        case 1 -> {
                            return 5;
                        }
                        case 2 -> {
                            return 8;
                        }
                        case 3 -> {
                            return 6;
                        }
                        case 4 -> {
                            return 7;
                        }
                        case 5 -> {
                            return 8;
                        }
                        case 6 -> {
                            return 8;
                        }
                        case 7 -> {
                            return 6;
                        }
                    }
                } else if (cLine[1] == cLine[2]) {
                    switch (i) {
                        case 0 -> {
                            return 0;
                        }
                        case 1 -> {
                            return 3;
                        }
                        case 2 -> {
                            return 6;
                        }
                        case 3 -> {
                            return 0;
                        }
                        case 4 -> {
                            return 1;
                        }
                        case 5 -> {
                            return 2;
                        }
                        case 6 -> {
                            return 0;
                        }
                        case 7 -> {
                            return 2;
                        }
                    }
                } else {
                    switch (i) {
                        case 0 -> {
                            return 1;
                        }
                        case 1 -> {
                            return 4;
                        }
                        case 2 -> {
                            return 7;
                        }
                        case 3 -> {
                            return 3;
                        }
                        case 4 -> {
                            return 4;
                        }
                        case 5 -> {
                            return 5;
                        }
                        case 6 -> {
                            return 4;
                        }
                        case 7 -> {
                            return 4;
                        }
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Checks if computer is blocking the player and should NOT move this piece
     * @param board, the game
     * @return int, the piece NOT to move, (0-8)
     */
    private int[] computerNotToMove(char[] board, int startLine) {
        for (int i = startLine; i < allLines; i++) {
            String line = getLine(board, i);
            int countO = 0;
            int countX = 0;
            char[] cLine = line.toCharArray();
            //det är k som används, hur skickar jag med den????
            for (int k = 0; k < cLine.length; k++) {
                if (cLine[k] == 'O')
                    countO++;
                else if (cLine[k] == 'X')
                    countX++;
            }
            if (countO == 2 && countX == 1) {
                //det finns två player och en computer på samma rad
                if (cLine[0] == cLine[1]) {
                    switch (i) {
                        case 0 -> {
                            return new int[]{2, i};
                        }
                        case 1 -> {
                            return new int[]{5, i};
                        }
                        case 2 -> {
                            return new int[]{8, i};
                        }
                        case 3 -> {
                            return new int[]{6, i};
                        }
                        case 4 -> {
                            return new int[]{7, i};
                        }
                        case 5 -> {
                            return new int[]{8, i};
                        }
                        case 6 -> {
                            return new int[]{8, i};
                        }
                        case 7 -> {
                            return new int[]{6, i};
                        }
                    }
                } else if (cLine[1] == cLine[2]) {
                    switch (i) {
                        case 0 -> {
                            return new int[]{0, i};
                        }
                        case 1 -> {
                            return new int[]{3, i};
                        }
                        case 2 -> {
                            return new int[]{6, i};
                        }
                        case 3 -> {
                            return new int[]{0, i};
                        }
                        case 4 -> {
                            return new int[]{1, i};
                        }
                        case 5 -> {
                            return new int[]{2, i};
                        }
                        case 6 -> {
                            return new int[]{0, i};
                        }
                        case 7 -> {
                            return new int[]{2, i};
                        }
                    }
                } else {
                    switch (i) {
                        case 0 -> {
                            return new int[]{1, i};
                        }
                        case 1 -> {
                            return new int[]{4, i};
                        }
                        case 2 -> {
                            return new int[]{7, i};
                        }
                        case 3 -> {
                            return new int[]{3, i};
                        }
                        case 4 -> {
                            return new int[]{4, i};
                        }
                        case 5 -> {
                            return new int[]{5, i};
                        }
                        case 6 -> {
                            return new int[]{4, i};
                        }
                        case 7 -> {
                            return new int[]{4, i};
                        }
                    }
                }
            }
        }
        return new int[]{-1, -1};
    }
    /**
     * Returns the line, column or diagonal as a string
     * @param board, the game
     * @param i, the number of the "line"
     * @return String, the line asked for eg. "X5X" or "OO3"
     */
    private String getLine(char[] board, int i) {
    String line = "";
        switch(i) {
            case 0 -> {
                return line.concat(Character.toString(board[0])).
                    concat(Character.toString(board[1])).concat(Character.toString(board[2]));
                }
            case 1 -> {
                return line.concat(Character.toString(board[3])).
                        concat(Character.toString(board[4])).concat(Character.toString(board[5]));
            }
            case 2 -> {
                return line.concat(Character.toString(board[6])).
                        concat(Character.toString(board[7])).concat(Character.toString(board[8]));
            }
            case 3 -> {
                return line.concat(Character.toString(board[0])).
                    concat(Character.toString(board[3])).concat(Character.toString(board[6]));
            }
            case 4 -> {
                return line.concat(Character.toString(board[1])).
                    concat(Character.toString(board[4])).concat(Character.toString(board[7]));
            }
            case 5 -> {
                return line.concat(Character.toString(board[2])).
                    concat(Character.toString(board[5])).concat(Character.toString(board[8]));
            }
            case 6 -> {

                return line.concat(Character.toString(board[0])).
                    concat(Character.toString(board[4])).concat(Character.toString(board[8]));
            }
            case 7 -> {
                return line.concat(Character.toString(board[2])).
                    concat(Character.toString(board[4])).concat(Character.toString(board[6]));
            }
        }
        return "-1";
    }
    /**
     * Checks to see if the piece sent in has won
     * @param board, the game
     * @param piece, the piece to check
     * @return boolean, true if the piece has won
     */
    private boolean hasWon(char[] board, char piece) {
        String win;
        if (piece == 'X')
            win = "XXX";
        else
            win = "OOO";
        for (int i = 0; i < allLines; i++) {
            String line = "";

            switch (i) {
                case 0 -> line = line.concat(Character.toString(board[0])).
                        concat(Character.toString(board[1])).concat(Character.toString(board[2]));
                case 1 -> line = line.concat(Character.toString(board[3])).
                        concat(Character.toString(board[4])).concat(Character.toString(board[5]));
                case 2 -> line = line.concat(Character.toString(board[6])).
                        concat(Character.toString(board[7])).concat(Character.toString(board[8]));
                case 3 -> line = line.concat(Character.toString(board[0])).
                        concat(Character.toString(board[3])).concat(Character.toString(board[6]));
                case 4 -> line = line.concat(Character.toString(board[1])).
                        concat(Character.toString(board[4])).concat(Character.toString(board[7]));
                case 5 -> line = line.concat(Character.toString(board[2])).
                        concat(Character.toString(board[5])).concat(Character.toString(board[8]));
                case 6 -> line = line.concat(Character.toString(board[0])).
                        concat(Character.toString(board[4])).concat(Character.toString(board[8]));
                case 7 -> line = line.concat(Character.toString(board[2])).
                        concat(Character.toString(board[4])).concat(Character.toString(board[6]));
            }
            if (line.equals(win))
                return true;
        }
        return false;
    }

    /**
     * Removes a piece from the board and sets the field to it's number
     * @param board, the game
     * @param field, the field to set to it's number instead of a piece (1-9)
     */
    private void removePiece(char[] board, int field) {
        board[field - 1] = Integer.toString(field).charAt(0);
    }
    /**
     * Makes a random move for the computer
     * @param board, the board to make move on
     * @param computersPiece, the piece to set
     * @param playersPiece, the players piece to check that the random move is not occupied by
     *      player
     */
    private void randomMove(char[] board, char computersPiece, char playersPiece) {
        Random random = new Random();
        while(true) {
            int field = random.nextInt(board.length);
            if (!(board[field] == playersPiece) && !(board[field] == computersPiece)) {
                board[field] = computersPiece;
                break;
            }
        }
    }

    /**
     * Player makes a move
     * @param board the game
     * @return boolean, true if a move was made
     */
    private boolean playerMove(char[] board) {
        int field = 0;
        int count = countPieces(board, playersPiece);
        if (count < 3) {
            while (true) {
                print.placePiece();
                field = getUserChoice();
                if (inBoard(board, field)) {
                    break;
                } else {
                    print.incorrectChoice();
                }
            }
            if (!makeMove(board, playersPiece, field)) {
                print.incorrectChoice();
                return false;
            }
        } else {
            while (true) {
                print.movePiece();
                field = getUserChoice();
                if (checkPieceExists(board, playersPiece, field)) {
                    removePiece(board, field);
                    break;
                } else {
                    print.incorrectChoice();
                }
            }
            while (true) {
                print.placePiece();
                field = getUserChoice();
                if (inBoard(board, field)) {
                    break;
                } else {
                    print.incorrectChoice();
                }
            }
            if (!makeMove(board, playersPiece, field)) {
                print.incorrectChoice();
                return false;
            }
        }
        return true;
    }

    /**
     * Checks to see that a piece is there to move
     * @param board, the game
     * @param piece, the piece to check
     * @param field, the field to check (1-9)
     * @return boolean, true if the piece is there to move
     */
    private boolean checkPieceExists(char[] board, char piece, int field) {
        return (board[field - 1] == piece);

    }

    /**
     * Count the number of pieces on the board
     * @param board, the game
     * @param piece, the piece to look for
     * @return int, the number of pieces
     */
    private int countPieces(char[] board, char piece) {
        int count = 0;
        for (char c : board) {
            if (c == piece) {
                count++;
            }
        }
        return count;
    }

    /**
     * Tries to make a move, only makes it if the field is free
     * @param board, the game
     * @param playersPiece, the players piece
     * @param field, the field where to put the piece
     * @return boolean, true if the move was made
     */
    private boolean makeMove(char[] board, char playersPiece, int field) {
        if (checkFieldFree(board, field)) {
            board[field - 1] = playersPiece;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Checks to see that the field is on the board
     * @param board the board to check
     * @param field the field to check for
     * @return boolean, true if the field is on the board
     */
    private boolean inBoard(char[] board, int field) {
        return field -1 < board.length && field - 1 >= 0;
    }

    /**
     * Prints statistics
     */
    protected void printStatistics() {
        print.printBoard(board);
    }

    /**
     * Checks to see if the field is occupied by a player or if it is free.
     * @param board the board to check the field
     * @param field the field to check
     * @return boolean, tru if the field is free
     */
    private boolean checkFieldFree(char[] board, int field) {
        return board[field - 1] != 'X' && board[field -1] != 'O';
    }

    /**
     * Calls to get the menu printed and returns the users answer
     * @return int, the users choice
     */
    protected int printMenuGetChoice() {
        print.printMainMenu();
        return getUserChoice();
    }

    /**
     * Waits to get the user choice from the prompt
     * @return int, the menu choice the user made, -1 if incorrect
     */
    private int getUserChoice() {
        int answer;
        try {
            answer = scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
        scanner.nextLine();
        return answer;
    }
    /**
     * Checks if computer has two in a row
     * @param board, the game
     * @return int[], the two fields not to move (0-8) -1 if nowhere to win
     */
    private int[] computerMoveAndWin(char[] board) {
        for (int i = 0; i < allLines; i++) {
            String line = getLine(board, i);
            int countO = 0;
            int countX = 0;
            char[] cLine = line.toCharArray();
            for (int k = 0; k < cLine.length; k++) {
                if (cLine[k] == 'O')
                    countO++;
                else if (cLine[k] == 'X') {
                    countX++;
                }
            }
            if (countX == 2 && countO == 0) {
                if (cLine[0] == cLine[1]) {
                    switch (i) {
                        case 0 -> {
                            return new int[] {0 ,1};
                        }
                        case 1 -> {
                            return new int[] {3, 4};
                        }
                        case 2 -> {
                            return new int[] {6, 7};
                        }
                        case 3 -> {
                            return new int[] {0, 3};
                        }
                        case 4 -> {
                            return new int[] {1, 4};
                        }
                        case 5 -> {
                            return new int[] {2, 5};
                        }
                        case 6 -> {
                            return new int[] {0, 4};
                        }
                        case 7 -> {
                            return new int[] {2, 4};
                        }
                    }
                } else if (cLine[1] == cLine[2]) {
                    switch (i) {
                        case 0 -> {
                            return new int[] {1, 2};
                        }
                        case 1 -> {
                            return new int[] {4, 5};
                        }
                        case 2 -> {
                            return new int[] {7, 8};
                        }
                        case 3 -> {
                            return new int[] {3, 6};
                        }
                        case 4 -> {
                            return new int[] {4, 7};
                        }
                        case 5 -> {
                            return new int[] {5, 8};
                        }
                        case 6 -> {
                            return new int[] {4, 8};
                        }
                        case 7 -> {
                            return new int[] {6, 4};
                        }
                    }
                } else {
                    switch (i) {
                        case 0 -> {
                            return new int[] {0, 2};
                        }
                        case 1 -> {
                            return new int[] {3, 5};
                        }
                        case 2 -> {
                            return new int[] {6, 8};
                        }
                        case 3 -> {
                            return new int[] {0, 6};
                        }
                        case 4 -> {
                            return new int[] {1, 7};
                        }
                        case 5 -> {
                            return new int[] {2, 8};
                        }
                        case 6 -> {
                            return new int[] {0, 8};
                        }
                        case 7 -> {
                            return new int[] {2, 6};
                        }
                    }
                }
            }
        }
        return new int[] {-1, -1};

    }
    /**
     * Checks if computer has two in a row
     * @param board, the game
     * @return int, the field to move to to win (0-8) -1 if nowhere to win
     */
    private int computerMoveToWin(char[] board) {
        for (int i = 0; i < allLines; i++) {
            String line = getLine(board, i);
            int countO = 0;
            int countX = 0;
            char[] cLine = line.toCharArray();
            for (int k = 0; k < cLine.length; k++) {
                if (cLine[k] == 'O')
                    countO++;
                else if (cLine[k] == 'X') {
                    countX++;
                }
            }
            if (countO == 0 && countX == 2) {
                //det finns två player på samma rad
                if (cLine[0] == cLine[1]) {
                    switch (i) {
                        case 0 -> {
                            return 2;
                        }
                        case 1 -> {
                            return 5;
                        }
                        case 2 -> {
                            return 8;
                        }
                        case 3 -> {
                            return 6;
                        }
                        case 4 -> {
                            return 7;
                        }
                        case 5 -> {
                            return 8;
                        }
                        case 6 -> {
                            return 8;
                        }
                        case 7 -> {
                            return 6;
                        }
                    }
                } else if (cLine[1] == cLine[2]) {
                    switch (i) {
                        case 0 -> {
                            return 0;
                        }
                        case 1 -> {
                            return 3;
                        }
                        case 2 -> {
                            return 6;
                        }
                        case 3 -> {
                            return 0;
                        }
                        case 4 -> {
                            return 1;
                        }
                        case 5 -> {
                            return 2;
                        }
                        case 6 -> {
                            return 0;
                        }
                        case 7 -> {
                            return 2;
                        }
                    }
                } else {
                    switch (i) {
                        case 0 -> {
                            return 1;
                        }
                        case 1 -> {
                            return 4;
                        }
                        case 2 -> {
                            return 7;
                        }
                        case 3 -> {
                            return 3;
                        }
                        case 4 -> {
                            return 4;
                        }
                        case 5 -> {
                            return 5;
                        }
                        case 6 -> {
                            return 4;
                        }
                        case 7 -> {
                            return 4;
                        }
                    }
                }
            }
        }
        return -1;
    }
}
