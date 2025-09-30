package Ljuslin.com;

public class TicTacToe2Main {
    public static void main(String[] args) {
        Methods m = new Methods();
        while (true) {
            switch (m.printMenuGetChoice()) {
                case 1 -> m.startNewGame();
                case 2 -> m.printStatistics();
                case 3 -> System.exit(0);
                default -> System.out.println("Incorrect choice, please try again");
            }
        }

    }

}
