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
     * Constructor, creates a new scanner
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
        boolean goOn = true;
        while(true) {
            goOn = true;
            while (goOn) {
                goOn = !playerMove(board);
            }
            print.printBoard(board);
            if (hasWon(board, playersPiece)) {
                print.playerWon();
                break;
            }
            computerMove(board, computersPiece, playersPiece);
            if (hasWon(board, computersPiece)) {
                print.printBoard(board);
                print.computerWon();
                break;
            }
            print.printBoard(board);
        }
    }

    /**
     *
     * @param board
     * @param computersPiece
     * @param playersPiece
     */
    private void computerMove(char[] board, char computersPiece, char playersPiece) {
        //kolla hur många pjäser computer har
        int count = 0;
        for(char c : board) {
            if (c == computersPiece) {
                count++;
            }
        }
        switch(count) {
            //computer har 0 pjäser, lägger första i mitten, annars random
            case 0 -> {
                computerMakesFirstMove(board, computersPiece, playersPiece);
            }
            //computer har 1 pjäs, player har två pjäser
            case 1 -> {
                computerMakesSecondMove(board, computersPiece, playersPiece);
            }
            case 2 -> {
                computerMakesThirdMove(board, computersPiece, playersPiece);
            }
        }
    }

    /**
     * Computer makes the first move
     * @param board, the ongoing game
     * @param computersPiece the computers piece
     * @param playersPiece the players piece
     */
    private void computerMakesFirstMove(char[] board, char computersPiece, char playersPiece) {
        if (board[4] == playersPiece) {
            randomMove(board, computersPiece, playersPiece);
        } else {
            board[4] = computersPiece;
        }
    }

    /**
     *
     * @param board
     * @param computersPiece
     * @param playersPiece
     */
    private  void computerMakesSecondMove(char[] board, char computersPiece, char playersPiece) {
        //check to see if player has two in a row somewhere
        int move = computerMoveToBlock(board);
        if (move == -1) {
            randomMove(board, computersPiece, playersPiece);
        } else {
            makeMove(board,computersPiece, move + 1);
        }
    }

    /**
     *
     * @param board
     * @param computersPiece
     * @param playersPiece
     * @return
     */
    private int computerMakesThirdMove(char[] board, char computersPiece, char playersPiece) {

    }
    /**
     *
      * @param board
     * @return
     */
    private int computerMoveToBlock(char[] board) {
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
            int count = 0;
            char[] cLine = line.toCharArray();
            for (int k = 0; k < cLine.length; k++) {
                if (cLine[k] == 'O')
                    count++;
            }
            if (count == 2) {
                //det finns två player på samma rad
                if (cLine[0] == cLine[1]) {
                    switch (i) {
                        case 0 -> {return 2;}
                        case 1 -> {return 5;}
                        case 2 -> {return 8;}
                        case 3 -> {return 6;}
                        case 4 -> {return 7;}
                        case 5 -> {return 8;}
                        case 6 -> {return 8;}
                        case 7 -> {return 6;}
                    }
                } else if (cLine[1] == cLine[2]) {
                    switch (i) {
                        case 0 -> {return 0;}
                        case 1 -> {return 3;}
                        case 2 -> {return 6;}
                        case 3 -> {return 0;}
                        case 4 -> {return 1;}
                        case 5 -> {return 2;}
                        case 6 -> {return 0;}
                        case 7 -> {return 2;}
                    }
                } else {
                    switch (i) {
                        case 0 -> {return 1;}
                        case 1 -> {return 4;}
                        case 2 -> {return 7;}
                        case 3 -> {return 3;}
                        case 4 -> {return 4;}
                        case 5 -> {return 5;}
                        case 6 -> {return 4;}
                        case 7 -> {return 4;}
                    }
                }
            }
        }
        return -1;
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
     *
     * @param game
     * @param piece
     */
    private void removePiece(char[] game, int piece) {
        game[piece - 1] = '\u0000';
    }
    /**
     * Checks to see if a piece exists from where a player tries to take his piece
     * @param game the board
     * @param piece the piece to move
     * @param field the row to check
     * @return boolean, true if the piece is there to move
     */
    private boolean checkPieceExists(char[] game, char piece, int field) {
        return (game[piece - 1] == piece);

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
     * Asks the user if he wants 'X' or 'O'
     */
    private void setPlayersPiece() {
        boolean goOn = true;
        while (goOn) {
            print.printPieceMenu();
            switch (getUserChoice()) {
                case 1 -> {
                    playersPiece = 'O';
                    computersPiece = 'X';
                    print.chooseO();
                    goOn = false;
                }
                case 2 -> {
                    playersPiece = 'X';
                    computersPiece = 'O';
                    print.chooseX();
                    goOn = false;
                }
                default -> print.incorrectChoice();
            }
        }
    }
    /**
     *
     * @param board
     * @return
     */
    private boolean playerMove(char[] board) {
        int field = 0;
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
        return true;
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
        return  (board[field - 1] == Integer.toString(field).charAt(0));
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
}
