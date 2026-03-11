import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(String color){
        super(color);
    }

    public void executeTurn(Board board, int roundNumber) {
        System.out.println("[" + roundNumber + "] / [" + getPlayerColor() + "] turn\n");

        System.out.println("Choose action:");
        System.out.println("1. Build Settlement");
        System.out.println("2. Build City");
        System.out.println("3. Build Road");
        System.out.println("4. Trade");
        System.out.println("5. Pass");

        

    }

    public void stepForward() {
        System.out.println("Type 'go' to continue...");
        
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("go")) {
                break;
            }
        }
    }
}
