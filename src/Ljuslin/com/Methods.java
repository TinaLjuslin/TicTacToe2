package Ljuslin.com;

import java.util.Scanner;

public class Methods {
    private final Scanner scanner;

    /**
     * Constructor, creates a new scanner
     */
    public Methods() {
        scanner = new Scanner(System.in);
    }

    /**
     * Starts a new game
     */
    protected void startNewGame() {

    }

    /**
     * Prints statistics
     */
    protected void printStatistics() {

    }

    /**
     * Calls to get the menu printed and returns the users answer
     * @return int, the users choice
     */
    protected int printMenuGetChoice() {
        printMenu();
        return getUserChoice();
    }

    /**
     * Prints the main menu for the user
     */
    private void printMenu() {
        System.out.println("1. Start new game");
        System.out.println("2. Print scores");
        System.out.println("3. Exit");
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
