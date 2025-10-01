package Ljuslin.com;

public class Printouts {
    protected  void incorrectChoice() {
        System.out.println("Incorrect choice, please try again");
    }

    /**
     * Prints the piece menu for the user
     */
    protected void printPieceMenu() {
        System.out.println("1. Choose 'O'");
        System.out.println("2. Choose 'X'");
        System.out.println("3. Exit");
    }
    protected void placePiece() {
        System.out.print("in which field would you like to place your piece: ");
    }
    protected void movePiece() {
        System.out.print("From which field would you like to move: ");
    }
    protected void playerWon() {
        System.out.println("Player has won!");
    }
    protected void computerWon() {
        System.out.println("Computer has won!");
    }

    /**
     * Prints the main menu for the user
     */
    protected void printMainMenu() {
        System.out.println("1. Start new game");
        System.out.println("2. Print scores");
        System.out.println("3. Exit");
    }

    /**
     * Prints the board
     * @param board the board to print
     */
    protected void printBoard(char[] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print(board[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}
